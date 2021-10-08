package it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.AsymmetricAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.key.{KeyPair, KeysGenerator}
import it.unibo.authsim.library.dsl.cryptography.util.Base64
import it.unibo.authsim.library.dsl.cryptography.cipher.{AsymmetricCipher, BasicCipher}

import java.security.*
import java.security.{KeyPairGenerator, KeyPair as JavaKeyPair}
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import javax.crypto.Cipher

/**
 * RSA cipher object
 */
object RSACipher:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  /**
   * Apply method for the object
   * @return        an istance of the RSA class
   */
  def apply() = new BasicRSACipher()

  /**
   * Basic implementation of an encrypter which use RSA algorithm for the cryptographic operation
   */
  case class BasicRSACipher() extends BasicCipher with AsymmetricCipher:
    /**
     * Variable representing the algorithm used for the cryptographic operation
     */
    val algorithm: RSA= RSA()
    
    /**
     * Variable representing a KeyFactory object that converts public/private keys of the RSA algorithm
     */
    private val keyFactory = KeyFactory.getInstance(algorithm.algorithmName)

    /**
     * Variable representing the defaul name for key pair file
     */
    private val defaultKeyFile="key.ser"

    /**
     * Method responsible of the key pair generation
     *
     * @param fileName                name of the file in which key pair should be saved
     *  @return                        the key pair generated
     */
    override def generateKeys(fileName: String= defaultKeyFile):KeyPair=
      KeysGenerator.generateKeys(fileName)

    /**
     * Method responsible of loading an existing key pair
     *
     * @param fileName                name of the file from which key should be loaded
     *  @return                        an istance of KeyPair
     */
    def loadKeys(fileName: String= defaultKeyFile):KeyPair=
      KeysGenerator.loadKeys(fileName)

    /**
     * Private method that perform the conversion from a flat string to a key a private Key properly
     * encoded
     * @param privateKeyString      private key in a string format
     * @tparam A                    type of the key to be encoded
     * @return                      a private key
     */
    private def privateKeyFromString[A](privateKeyString: A): PrivateKey =
      val bytes = Base64.decodeToArray(privateKeyString)
      val spec = new PKCS8EncodedKeySpec(bytes)
      keyFactory.generatePrivate(spec)

    /**
     * Private method that perform the conversion from a flat string to a key a public key properly
     * encoded
     * @param privateKeyString      public key in a string format
     * @tparam A                    type of the key to be encoded
     * @return                      a public key
     */
    private def publicKeyFromString[A](publicKeyString: A): PublicKey =
      val bytes = Base64.decodeToArray(publicKeyString)
      val spec = new X509EncodedKeySpec(bytes)
      keyFactory.generatePublic(spec)

    /**
     * Method that performs the encryption and decryption tasks
     *
     * @param mode                    Mode in with the method must operate, either as a decrypter or an encrypter
     * @param password                Password to be encrypted or decrypted
     * @param inputKey
     * @tparam A                      Generic parameter for the password
     * @tparam B                      Generic parameter for the secret
     *  @return                        A string representing the password either encrypted or decrypted
     */
    override def crypto[A,B](mode:EncryptionMode, password: A, inputKey: B): String=
      val cipher = Cipher.getInstance(algorithm.algorithmName);
      mode match{
        case EncryptionMode.Encryption =>
          val key = privateKeyFromString(inputKey)
          cipher.init(Cipher.ENCRYPT_MODE, key)
          Base64.encodeToString(cipher.doFinal(password))
        case EncryptionMode.Decryption =>
          val key = publicKeyFromString(inputKey)
          cipher.init(Cipher.DECRYPT_MODE, key)
          new String(cipher.doFinal(Base64.decodeToArray(password)))
      }