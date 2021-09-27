package it.unibo.authsim.library.dsl.cryptography.asymmetric

import it.unibo.authsim.library.dsl.cryptography.{Keys}
import it.unibo.authsim.library.dsl.cryptography.util.{CostumBase64 as Base64, DiskManager as Disk}

import java.io.{File, FileInputStream, FileOutputStream, ObjectOutputStream, PrintWriter}
import scala.io.Source
import java.security.{KeyPair, KeyPairGenerator}

import java.security.spec.PKCS8EncodedKeySpec
//Singleton
object PersistentKeysGenerator:
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

  private def generateKeyPair: KeyPair=
    val keyGen = KeyPairGenerator.getInstance(_algorithmName)
    keyGen.initialize(_bitlength)
    keyGen.generateKeyPair

  private def key(keypair:KeyPair): Keys =
    new Keys:
      def publicKey: String=
        val bytes: Array[Byte] = keypair.getPublic.getEncoded
        Base64.encodeToString(bytes)

      def privateKey: String=
        val bytes: Array[Byte] = keypair.getPrivate.getEncoded
        Base64.encodeToString(bytes)

  def generateKeys(): Keys =
    val keypair = generateKeyPair
    saveKeys(keypair:KeyPair)

  def loadKeys(fileName: String = "key.ser"): Keys =
    if (Disk.isExisting(fileName))then
      val kp=Disk.loadObject[KeyPair](fileName)
      key(kp.asInstanceOf[KeyPair])
    else
      generateKeys()

  private def saveKeys(keypair:KeyPair):Keys=
    Disk.saveObject(keypair, "prova.ser")
    key(keypair)