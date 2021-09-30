package it.unibo.authsim.library.dsl.cryptography.asymmetric

import it.unibo.authsim.library.dsl.cryptography.util.Base64
import it.unibo.authsim.library.dsl.cryptography.asymmetric.KeyPair
import it.unibo.authsim.library.dsl.cryptography.{BasicEcryption, CryptographicAlgorithm}
import it.unibo.authsim.library.dsl.cryptography.asymmetric.{AsymmetricEncryption, PersistentKeysGenerator}

import java.security.*
import java.security.{KeyPairGenerator, KeyPair as JavaKeyPair}
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import javax.crypto.Cipher

trait RSA extends AsymmetricEncryption with KeyGenerator[KeyPair]
  
object RSA:
  import it.unibo.authsim.library.dsl.cryptography.asymmetric.PersistentKeysGenerator

  def apply()= new BasicRSA()

  class BasicRSA() extends BasicEcryption with RSA:
    import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

    override val _name = "RSA"

    private val keyFactory = KeyFactory.getInstance(_name)

    private val defaultKeyFile="key.ser"

    override def generateKeys(fileName: String= defaultKeyFile):KeyPair=
      PersistentKeysGenerator.generateKeys(fileName)

    def loadKeys(fileName: String= defaultKeyFile):KeyPair=
      PersistentKeysGenerator.loadKeys(fileName)

    private def privateKeyFromString[A](privateKeyString: A): PrivateKey =
      val bytes = Base64.decodeToArray(privateKeyString)
      val spec = new PKCS8EncodedKeySpec(bytes)
      keyFactory.generatePrivate(spec)

    private def publicKeyFromString[A](publicKeyString: A): PublicKey =
      val bytes = Base64.decodeToArray(publicKeyString)
      val spec = new X509EncodedKeySpec(bytes)
      keyFactory.generatePublic(spec)

    override def crypto[A,B](mode:EncryptionMode, password: A, inputKey: B): String=
      val cipher = Cipher.getInstance(_name);
      mode match{
        case EncryptionMode.Encryption =>
          val key = publicKeyFromString(inputKey)
          cipher.init(Cipher.ENCRYPT_MODE, key)
          Base64.encodeToString(cipher.doFinal(password))
        case EncryptionMode.Decryption =>
          val key = privateKeyFromString(inputKey)
          cipher.init(Cipher.DECRYPT_MODE, key)
          new String(cipher.doFinal(Base64.decodeToArray(password)))
      }