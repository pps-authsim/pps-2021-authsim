package it.unibo.authsim.library.dsl.cryptography.asymmetric

import it.unibo.authsim.library.dsl.cryptography.{Keys}
import it.unibo.authsim.library.dsl.cryptography.util.{CostumBase64 as Base64, DiskManager as Disk}

import java.security.{KeyPair, KeyPairGenerator}

import java.security.spec.PKCS8EncodedKeySpec

object RSAPersistentKeysGenerator:
  private def generateKeyPair: KeyPair=
    val keyGen = KeyPairGenerator.getInstance("RSA")
    keyGen.initialize(2048)
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
    Disk.saveObject(keypair, "key.ser")
    key(keypair)


object App5:
  def  main(args: Array[String]): Unit =
    val keyGenerator= RSAPersistentKeysGenerator
    val keypair=keyGenerator.generateKeys()
    val keyPair2=keyGenerator.loadKeys()