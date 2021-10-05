package it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.CaesarCipher
import it.unibo.authsim.library.dsl.cryptography.encrypter.Cipher

/**
 * Caesar Cipher cipher object
 */
object CaesarCipherCipher:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  /**
   * Apply method for the object
   * 
   * @return                           an istance of the Caesar Cipher class
   */
  def apply(): Cipher = new CaesarCipherImpl()

  /**
   * Basic implementation of an encrypter which use Caesar Cipher algorithm for the cryptographic operation
   */
  private case class CaesarCipherImpl() extends Cipher:
    /**
     * Variable representing the algorithm used for the cryptographic operation
     */
    var algorithm : CaesarCipher = CaesarCipher()

    /**
     * Method used to encrypt the password
     * 
     * @param password                password to be encrypted
     * @param rotation                shift to be applied
     * @tparam A                      generic parameter for the password
     * @tparam B                      generic parameter for the secret, if it is not an integer or cannot be cast to one
     *                                in a reasonable way it is substitued with the default value, which is 0
     *  @return                       a string representing the password encrypted
     */
    override def encrypt[A,B](password: A, rotation: B): String =  crypto(password, rotation)

    /**
     * Method used to decrypt the password
     * 
     * @param password               password to be decrypted
     * @param rotation               shift to be applied
     * @tparam A                     generic parameter for the password
     * @tparam B                     generic parameter for the secret, if it is not an integer or cannot be cast to one
     *                               in a reasonable way it is substitued with the default value, which is 0
     *  @return                      a string representing the password decrypted
     */
    override def decrypt[A,B](password: A, rotation: B): String = crypto(password,-rotation)

    /**
     * Method that performs the encryption and decryption tasks
     * @param password      Password to be encrypted or decrypted
     * @param rotation      value that represents the shift
     * @return              Either the password encrypted or decrypted
     */ 
    private def crypto(password: String, rotation:Int) = password.map(c => (rotation + c).toChar)