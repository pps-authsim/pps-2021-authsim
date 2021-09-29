package it.unibo.authsim.library.dsl.cryptography.asymmetric

import it.unibo.authsim.library.dsl.cryptography.asymmetric.KeyPair
import it.unibo.authsim.library.dsl.cryptography.util.{Base64 , DiskManager as Disk}

import java.io.{File, FileInputStream, FileOutputStream, ObjectOutputStream, PrintWriter}
import scala.io.Source
import java.security.{KeyPair as JavaKeyPair, KeyPairGenerator}
import java.security.spec.PKCS8EncodedKeySpec


trait KeyGenerator:
  def generateKeys(): KeyPair
  def loadKeys(fileName: String):KeyPair

//Singleton
object PersistentKeysGenerator extends KeyGenerator:
  private var algorithmSet =Set("RSA", "DiffieHellman")
  private var keySet = Set(1024, 2048, 4096)
  private var _bitlength= 2048
  private var _algorithmName= "RSA"

  def algorithmName(): String = _algorithmName

  def bitLength_(keylength: Int): Unit = if(keySet.contains(keylength)) then _bitlength=keylength else println("Invalid bit size")

  def bitLength: Int= _bitlength

  private def algorithmName_(name:String): Unit =
    if(algorithmSet.contains(_algorithmName)) then
      this._algorithmName=name
    else println("Invalid algorithm name")

  private def generateKeyPair: JavaKeyPair=
    val keyGen = KeyPairGenerator.getInstance(_algorithmName)
    keyGen.initialize(_bitlength)
    keyGen.generateKeyPair

  private def key(keypair:JavaKeyPair): KeyPair =
    new KeyPair:
      def publicKey: String=
        val bytes: Array[Byte] = keypair.getPublic.getEncoded
        Base64.encodeToString(bytes)

      def privateKey: String=
        val bytes: Array[Byte] = keypair.getPrivate.getEncoded
        Base64.encodeToString(bytes)

  def generateKeys(): KeyPair =
    val keypair = generateKeyPair
    saveKeys(keypair:JavaKeyPair)

  def loadKeys(fileName: String = "key.ser"): KeyPair =
    if (Disk.isExisting(fileName))then
      val kp=Disk.loadObject[JavaKeyPair](fileName)
      key(kp.asInstanceOf[JavaKeyPair])
    else
      generateKeys()

  private def saveKeys(keypair:JavaKeyPair) : KeyPair=
    Disk.saveObject(keypair, "prova.ser")
    key(keypair)