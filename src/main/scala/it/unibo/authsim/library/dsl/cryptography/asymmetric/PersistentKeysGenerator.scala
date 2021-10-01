package it.unibo.authsim.library.dsl.cryptography.asymmetric

import it.unibo.authsim.library.dsl.cryptography.asymmetric.KeyPair
import it.unibo.authsim.library.dsl.cryptography.util.{Base64 , DiskManager as Disk}
import java.io.{File, FileInputStream, FileOutputStream, ObjectOutputStream, PrintWriter}
import scala.io.Source
import java.security.{KeyPair as JavaKeyPair, KeyPairGenerator}
import java.security.spec.PKCS8EncodedKeySpec

trait KeyGenerator[A]:
  def generateKeys(fileName: String): A
  def loadKeys(fileName: String):A

//Singleton
object PersistentKeysGenerator extends KeyGenerator[KeyPair] :

  private var algorithmSet = Set("RSA", "DiffieHellman")
  private var keySet = Set(1024, 2048, 4096)
  private var _bitlength= 2048
  private var _algorithmName= "RSA"

  def algorithmName(): String = _algorithmName

  def algorithmName_(name:String): Unit =
    if(algorithmSet.contains(_algorithmName)) then
      this._algorithmName = name
    else println("Invalid algorithm name")

  def bitLength_(keylength: Int): Unit =
    if(keySet.contains(keylength)) then
      _bitlength=keylength
    else println("Invalid bit size")

  def bitLength: Int= _bitlength

  override def generateKeys(fileName: String): KeyPair =
    val keyGen = KeyPairGenerator.getInstance(_algorithmName)
    keyGen.initialize(_bitlength)
    val keyPair = keyGen.generateKeyPair
    saveKeys(keyPair, fileName)
    keyPair

  override def loadKeys(fileName: String): KeyPair =
    if (Disk.isExisting(fileName))then
      val optKey=Disk.loadObject(fileName)
      optKey.getOrElse(generateKeys(fileName))
    else
      generateKeys(fileName)

  private def saveKeys(keypair:KeyPair, fileName: String) : Unit=

    Disk.saveObject(keypair, fileName)

  implicit class KeyPairImpl(keypair:JavaKeyPair) extends Serializable with KeyPair:

    implicit def encode(key:  Array[Byte]): String = Base64.encodeToString(key)

    def publicKey: String= keypair.getPublic.getEncoded

    def privateKey: String= keypair.getPrivate.getEncoded