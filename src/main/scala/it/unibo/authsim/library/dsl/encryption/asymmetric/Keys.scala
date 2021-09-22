package it.unibo.authsim.library.dsl.encryption.asymmetric

import it.unibo.authsim.library.dsl.encryption.{Keys,PersistentKeyGenerator}
import it.unibo.authsim.library.dsl.encryption.util.{CostumBase64, DiskManager as Disk}

import java.security.{KeyPair, KeyPairGenerator}

import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64

class PersistentKeysGeneratorImpl extends PersistentKeyGenerator {
  private val bitLength: Int =  2048
  var _algorithmName:String="RSA"
  private var _publicKey =""
  private var _privateKey =""
  override def algorithmName= _algorithmName
  override def algorithmName_(newAlgorithmName:String)=
    //TODO check if algorithm exists
    _algorithmName=newAlgorithmName

  private def generateKeyPair: KeyPair=
    val keyGen = KeyPairGenerator.getInstance(_algorithmName)
    keyGen.initialize(bitLength)
    keyGen.generateKeyPair

  private def key(keypair:KeyPair): Keys =
    new Keys:
      def publicKey: String=
        val bytes: Array[Byte] = keypair.getPublic.getEncoded
        _publicKey = CostumBase64.encodeToString(bytes)
        _publicKey

      def privateKey: String=
        val bytes: Array[Byte] = keypair.getPrivate.getEncoded
        _privateKey= CostumBase64.encodeToString(bytes)
        _privateKey

  override def generateKeys(): Keys =
    val keypair = generateKeyPair
    key(keypair)

  def loadOrCreate(fileName: String = "key.ser"): Keys =
    val keyPair =
      if (Disk.isExisting(fileName))then
        Disk.loadObject[KeyPair](fileName)
      else {
        val kp = generateKeys()
        Disk.saveObject(kp, fileName)
        kp
      }
    key(keyPair.asInstanceOf[KeyPair])

  def privateKey: String = _privateKey
  def publicKey: String = _publicKey

}

object App5:
  def  main(args: Array[String]): Unit =
    val keyGenerator= new PersistentKeysGeneratorImpl()
    val keypair=keyGenerator.generateKeys()
    println(keypair.publicKey.toString)
    println(keypair.privateKey.toString)
