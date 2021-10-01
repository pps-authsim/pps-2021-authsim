package it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.AsymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key.{KeyPair, KeysGenerator}
import it.unibo.authsim.library.dsl.cryptography.util.Base64
import it.unibo.authsim.library.dsl.cryptography.encrypter.{AsymmetricEncrypter, BasicEcrypter}

import java.security.*
import java.security.{KeyPairGenerator, KeyPair as JavaKeyPair}
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import javax.crypto.Cipher

object RSAEncrypter extends BasicEcrypter with AsymmetricEncrypter:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  var algorithm: RSA= RSA()
  
  private val keyFactory = KeyFactory.getInstance(algorithm.algorithmName)

  private val defaultKeyFile="key.ser"

  override def generateKeys(fileName: String= defaultKeyFile):KeyPair=
    KeysGenerator.generateKeys(fileName)

  def loadKeys(fileName: String= defaultKeyFile):KeyPair=
    KeysGenerator.loadKeys(fileName)

  private def privateKeyFromString[A](privateKeyString: A): PrivateKey =
    val bytes = Base64.decodeToArray(privateKeyString)
    val spec = new PKCS8EncodedKeySpec(bytes)
    keyFactory.generatePrivate(spec)

  private def publicKeyFromString[A](publicKeyString: A): PublicKey =
    val bytes = Base64.decodeToArray(publicKeyString)
    val spec = new X509EncodedKeySpec(bytes)
    keyFactory.generatePublic(spec)

  override def crypto[A,B](mode:EncryptionMode, password: A, inputKey: B): String=
    val cipher = Cipher.getInstance(algorithm.algorithmName);
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