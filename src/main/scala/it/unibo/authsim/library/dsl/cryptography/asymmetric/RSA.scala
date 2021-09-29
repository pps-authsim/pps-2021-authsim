package it.unibo.authsim.library.dsl.cryptography.asymmetric

import it.unibo.authsim.library.dsl.cryptography.util.Base64
import it.unibo.authsim.library.dsl.cryptography.asymmetric.KeyPair
import it.unibo.authsim.library.dsl.cryptography.{AsymmetricEncryption, CryptographicAlgorithm}
import it.unibo.authsim.library.dsl.cryptography.asymmetric.PersistentKeysGenerator

import java.security.*
import java.security.{KeyPair as JavaKeyPair, KeyPairGenerator}
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import javax.crypto.Cipher

trait RSA extends AsymmetricEncryption with KeyGenerator
  
object RSA:
  import it.unibo.authsim.library.dsl.cryptography.asymmetric.PersistentKeysGenerator
  def apply()= new RSA() :
    import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

    private var _name = "RSA"
    private val keyFactory = KeyFactory.getInstance(_name)

    override  def algorithmName: String= this.toString
    override def generateKeys():KeyPair=
      PersistentKeysGenerator.generateKeys()

    def loadKeys(fileName: String = "key.ser"):KeyPair=
      PersistentKeysGenerator.loadKeys()

    private def privateKeyFromString(privateKeyString: String): PrivateKey =
      val bytes = Base64.decodeToBytes(privateKeyString)
      val spec = new PKCS8EncodedKeySpec(bytes)
      keyFactory.generatePrivate(spec)

    private def publicKeyFromString(publicKeyString: String): PublicKey =
      val bytes = Base64.decodeToBytes(publicKeyString)
      val spec = new X509EncodedKeySpec(bytes)
      keyFactory.generatePublic(spec)

    private def publicKeyStringFromKeyPair(kp: JavaKeyPair): String =
      val bytes: Array[Byte] = kp.getPublic.getEncoded
      Base64.encodeToString(bytes)

    private def privateKeyStringFromKeyPair(kp: JavaKeyPair): String =
      val bytes: Array[Byte] = kp.getPrivate.getEncoded
      Base64.encodeToString(bytes)

    override def decrypt[A, B](encrypted: A, privateKey: B): String =
      crypto(EncryptionMode.Decryption, encrypted, privateKey)

    override def encrypt[A, B](secret: A, publicKey: B): String =
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