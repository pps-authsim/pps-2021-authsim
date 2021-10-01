package it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key

import it.unibo.authsim.library.dsl.cryptography.algorithm.AsymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.util.{Base64, DiskManager as Disk}
import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import java.io.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.{KeyPairGenerator, KeyPair as JavaKeyPair}
import scala.io.Source

trait KeyGenerator[A]:
  def algorithm_(algorithm: AsymmetricEncryptionAlgorithm): Unit
  def algorithm: AsymmetricEncryptionAlgorithm
  def generateKeys(fileName: String): A
  def loadKeys(fileName: String):A

//Singleton
object KeysGenerator extends KeyGenerator[KeyPair]:
  private var _algorithm: AsymmetricEncryptionAlgorithm = RSA()

  def algorithm_(algorithm: AsymmetricEncryptionAlgorithm): Unit=
    _algorithm=algorithm
    
  def algorithm: AsymmetricEncryptionAlgorithm=
    _algorithm

  override def generateKeys(fileName: String): KeyPair =
    val keyGen = KeyPairGenerator.getInstance(_algorithm.algorithmName)
    keyGen.initialize(_algorithm.keyLength)
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