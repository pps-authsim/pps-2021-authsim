package it.unibo.authsim.library.dsl.cryptography.asymmetric

import it.unibo.authsim.library.dsl.cryptography.util.CostumBase64 as Base64
import it.unibo.authsim.library.dsl.cryptography.{CryptographicAlgorithm, AsymmetricEncryption, EncryptionMode, Keys, KeyGenerator}

import java.security.*
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import javax.crypto.Cipher

trait RSA extends AsymmetricEncryption with KeyGenerator:
  def generateKeys(): Keys

object RSA:
  def apply()= new RSA() :
    private var _name = "RSA"
    private val keyFactory = KeyFactory.getInstance(_name)
    private val keysGenerator= RSAPersistentKeysGenerator

    override  def algorithmName: String= this.toString
    override def generateKeys():Keys=
      keysGenerator.generateKeys()

    def loadKeys(fileName: String = "key.ser"):Keys=
      keysGenerator.loadKeys()

    private def privateKeyFromString(privateKeyString: String): PrivateKey =
      val bytes = Base64.decodeToBytes(privateKeyString)
      val spec = new PKCS8EncodedKeySpec(bytes)
      keyFactory.generatePrivate(spec)

    private def publicKeyFromString(publicKeyString: String): PublicKey =
      val bytes = Base64.decodeToBytes(publicKeyString)
      val spec = new X509EncodedKeySpec(bytes)
      keyFactory.generatePublic(spec)

    private def publicKeyStringFromKeyPair(kp: KeyPair): String =
      val bytes: Array[Byte] = kp.getPublic.getEncoded
      Base64.encodeToString(bytes)

    private def privateKeyStringFromKeyPair(kp: KeyPair): String =
      val bytes: Array[Byte] = kp.getPrivate.getEncoded
      Base64.encodeToString(bytes)

    override def decrypt(encrypted: String, privateKey: String): String =
      crypto(EncryptionMode.Decryption, encrypted, privateKey)

    override def encrypt(secret: String, publicKey: String): String =
      crypto(EncryptionMode.Encryption, secret, publicKey)

    private def crypto(mode:EncryptionMode, password: String, inputKey: String): String=
      val cipher = Cipher.getInstance(_name);
      mode match{
        case EncryptionMode.Encryption =>
          val key = publicKeyFromString(inputKey)
          cipher.init(Cipher.ENCRYPT_MODE, key)
          Base64.encodeToString(cipher.doFinal(password.getBytes("UTF8")))
        case EncryptionMode.Decryption =>
          val key = privateKeyFromString(inputKey)
          cipher.init(Cipher.DECRYPT_MODE, key)
          new String(cipher.doFinal(Base64.decodeToBytes(password)))
      }
    override def toString: String = "RSA"

object App4:
  def main(args: Array[String]): Unit =
    val secret = "password"
    val rsa = RSA()
    val pk = rsa.generateKeys()
    println("private key"+ pk)
    val priv = pk.privateKey
    val publ= pk.publicKey
    println("private pair "+ (publ, priv))
    val encrypted = rsa.encrypt(secret, publ)
    println("password encrypted: "+ encrypted)
    val decrypted = rsa.decrypt(encrypted, priv)
    println("password encrypted: "+ decrypted)
    val pk2=rsa.loadKeys("ket.ser")
    val priv2 = pk2.privateKey
    val publ2= pk2.publicKey
    println("private pair "+ (publ2, priv2))
    val encrypted2 = rsa.encrypt(secret, publ2)
    println("password encrypted: "+ encrypted2)
    val decrypted2 = rsa.decrypt(encrypted2, priv2)
    println("password encrypted: "+ decrypted2)