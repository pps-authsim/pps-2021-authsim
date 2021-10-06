package it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.util.Base64
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.AES
import it.unibo.authsim.library.dsl.cryptography.encrypter.BasicCipher

import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, SecretKeySpec}
import java.util.*
import scala.util.Random

/**
 * AES cipher object
 */
object AESCipher:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._  
  
  /**
   * Apply method for the object
   * @return        an istance of the AES class
   */
  def apply() = new AESCipherterImpl()
  /**
   * Basic implementation of an encrypter which use AES algorithm for the cryptographic operation
   */
  case class AESCipherterImpl() extends BasicCipher:

    /**
     * Variable representing the algorithm used for the cryptographic operation
     */
    var algorithm : AES = AES()

    /**
     * Private variable that specify which transformation must be applied from the Cipher
     */
    private val _trasformation: String = "AES/ECB/PKCS5PADDING"

    private val defautlSalt: String = Random.alphanumeric.filter(_.isLetterOrDigit).take(Random.between(4,5))
    /**
     * Method that performs the encryption and decryption tasks
     *
     * @param mode                    Mode in with the method must operate, either as a decrypter or an encrypter
     * @param password                Password to be encrypted or decrypted
     * @param secret                  Secred to encrypt or decrypt the password
     * @tparam A                      Generic parameter for the password
     * @tparam B                      Generic parameter for the secret
     *  @return                        A string representing the password either encrypted or decrypted
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
     * Private method repsonsible of the creation of the SecretKeySpecification
     *
     * @param secret          string value to be used in the creation of SecretKeySpecification
     * @return                a SecretKeySpecification compliant with algorithm chosen
     */
    private def secretKeySpec(secret: String): SecretKeySpec =
      var keyBytes: Array[Byte] = secret.concat(algorithm.salt.getOrElse(defautlSalt))
      keyBytes= Arrays.copyOf(keyBytes, algorithm.keyLength)
      new SecretKeySpec(keyBytes, algorithm.algorithmName)
