package it.unibo.authsim.library.dsl.encryption.asymmetric

import it.unibo.authsim.library.dsl.encryption.util.CostumBase64 as Base64
import it.unibo.authsim.library.dsl.encryption.{Algorithm, AsymmetricEncryption, EncryptionMode, Keys, SymmetricEncryption}

import java.security.*
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import javax.crypto.Cipher
import java.util.Base64 as JavaBase64

trait RSA extends AsymmetricEncryption with Algorithm:
  def generateKeys(): Keys

object RSA:
  def apply()= new RSA() :
    private var _name = "RSA"
    private val keyFactory = KeyFactory.getInstance(_name)
    private val keysGenerator= new PersistentKeysGeneratorImpl()
    override def algorithmName: String= _name

    override def generateKeys():Keys=
      keysGenerator.algorithmName_(_name)
      keysGenerator.generateKeys()

    private def privateKeyFromString(privateKeyString: String): PrivateKey =
      val bytes = JavaBase64.getDecoder.decode(privateKeyString)
      val spec = new PKCS8EncodedKeySpec(bytes)
      keyFactory.generatePrivate(spec)

    private def publicKeyFromString(publicKeyString: String): PublicKey =
      val bytes = JavaBase64.getDecoder.decode(publicKeyString)
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

object App4:
  def main(args: Array[String]): Unit =
    val secret = "The Earth is Flat"
    //val pk = RSA.PersistentKeys.loadOrCreate(fileName = "keys.ser")
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