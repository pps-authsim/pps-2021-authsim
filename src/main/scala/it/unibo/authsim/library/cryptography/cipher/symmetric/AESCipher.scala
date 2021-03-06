package it.unibo.authsim.library.cryptography.cipher.symmetric

import it.unibo.authsim.library.cryptography.algorithm.SymmetricAlgorithm
import it.unibo.authsim.library.cryptography.util.Base64
import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.cryptography.algorithm.symmetric.AES
import it.unibo.authsim.library.cryptography.cipher.{BasicCipher, SymmetricCipher}

import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, SecretKeySpec}
import java.util.*
import scala.util.Random

/**
 * AES cipher object.
 */
object AESCipher:
  import it.unibo.authsim.library.cryptography.util.ImplicitConversion.ImplicitConversionToBuiltinType._
  import it.unibo.authsim.library.cryptography.util.ImplicitConversion.ImplicitToArray._
  
  /**
   * Apply method for the object.
   * 
   * @return : an istance of the AES class
   */
  def apply() = new BasicAESCipher()
  
  /**
   * Basic implementation of an encrypter which use AES algorithm for the cryptographic operation.
   */
  case class BasicAESCipher() extends BasicCipher with SymmetricCipher:

    /**
     * Variable representing the algorithm used for the cryptographic operation.
     */
    override val algorithm : AES = AES()

    /**
     * Private variable that specify which transformation must be applied from the Cipher.
     */
    private val _trasformation: String = "AES/ECB/PKCS5PADDING"

    /**
     * Private variable representing the salt value to be used during the cryptographic operations.
     */
    private val salt = Arrays.copyOf(algorithm.salt, 8)

    /**
     * Method that performs the encryption and decryption tasks.
     *
     * @param mode : mode in with the method must operate, either as a decrypter or an encrypter
     * @param password : password to be encrypted or decrypted
     * @param secret : secred to encrypt or decrypt the password
     * @tparam A : generic parameter for the password
     * @tparam B : generic parameter for the secret
     *  @return : a string representing the password either encrypted or decrypted
     */
    override def crypto[A,B](mode:EncryptionMode, password: A, secret: B): String=
      val cipher: Cipher = Cipher.getInstance(_trasformation)
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec(secret))
          new String(Base64.encodeToArray(cipher.doFinal(password)))
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, secretKeySpec(secret))
          new String(cipher.doFinal(Base64.decodeToArray(password)))
      }

    /**
     * Private method repsonsible of the creation of the SecretKeySpecification.
     *
     * @param secret : string value to be used in the creation of SecretKeySpecification
     * @return : a SecretKeySpecification compliant with algorithm chosen
     */
    private def secretKeySpec(secret: String): SecretKeySpec =
      var keyBytes: Array[Byte] = secret.concat(salt)
      keyBytes= Arrays.copyOf(keyBytes, algorithm.keyLength)
      new SecretKeySpec(keyBytes, algorithm.name)