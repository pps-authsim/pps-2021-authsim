package it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.DES
import it.unibo.authsim.library.dsl.cryptography.encrypter.BasicCipher
import it.unibo.authsim.library.dsl.cryptography.util.Base64

import java.io.*
import java.security.spec.*
import java.util.Arrays
import javax.crypto.*
import javax.crypto.spec.*

/**
 * DES cipher object
 */
object DESCipher:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  /**
   * Apply method for the object
   * @return        an istance of the DES class
   */
  def apply() = new DESCipherImpl()
  /**
   * Basic implementation of an cipher which use DES algorithm for the cryptographic operation
   */
  case class DESCipherImpl() extends BasicCipher:
    /**
     * Variable representing the algorithm used for the cryptographic operation
     */
    var algorithm : DES = DES()
    
    /**
     * Private variable representing the salt value to be used during the cryptographic operations
     */
    private val salt = Arrays.copyOf(algorithm.salt, 8)
    /**
     * Private variable that represent the number of iterations used in the generation of 
     * algorithm params
     */
    private var _iterationCount: Int = 19

    /**
     * Private variable that specify which transformation must be applied from the Cipher
     */
    private val _trasformation : String = "PBEWithMD5AndDES"

    /**
     * Setter for the iteration value 
     * 
     * @param iteration         new iterations value
     */
    def iterationCount_(iteration: Int): Unit =
      _iterationCount = iteration
    
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
    override def crypto[A, B](mode:EncryptionMode, password: A, secret: B): String=
      var cipher = Cipher.getInstance(_trasformation)
      var _secretKey: SecretKey = secretKey(secret)
      var paramSpec: AlgorithmParameterSpec = new PBEParameterSpec(salt, _iterationCount)
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, _secretKey, paramSpec)
          new String(Base64.encodeToArray(cipher.doFinal(password)))
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, _secretKey, paramSpec)
          new String(cipher.doFinal(Base64.decodeToArray(password)))
      }

    /**
     * Private method repsonsible of the creation of the SecretKey
     *
     * @param secret      string value to be used in the creation of SecretKey
     * @return            a new SecretKey
     */
    private def secretKey(secret: String): SecretKey =
      var keySpec: KeySpec = new PBEKeySpec(secret, salt ,_iterationCount)
      SecretKeyFactory.getInstance(_trasformation).generateSecret(keySpec)