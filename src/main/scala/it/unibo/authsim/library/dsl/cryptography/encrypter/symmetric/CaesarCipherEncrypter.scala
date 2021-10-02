package it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.CaesarCipher
import it.unibo.authsim.library.dsl.cryptography.encrypter.Encrypter

/**
 * Caesar Cipher encrypter object
 */
object CaesarCipherEncrypter:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  /**
   * Apply method for the object
   * 
   * @return                           an istance of the Caesar Cipher class
   */
  def apply() = new CaesarCipherEncrypterImpl()

  /**
   * Basic implementation of an encrypter which use Caesar Cipher algorithm for the cryptographic operation
   */
  case class CaesarCipherEncrypterImpl() extends Encrypter:
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
    override def encrypt[A,B](password: A, rotation: B): String = crypto(password, rotation)

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
    private def crypto(password: String, rotation:Int)=
      password.toLowerCase.map {
        case character
          if algorithm.alphabet.contains(character) =>
            shift(algorithm.alphabet, character, rotation)
        case character => character
      }

    /**
     * Private method that performs the shift operation 
     * @param alpha           alphabet to be used
     * @param character       character of the password to be considered
     * @param rotation        shift to applied to character
     * @return                the new character
     */
    private def shift(alpha:IndexedSeq[Char], character:Char, rotation:Int)=
      alpha((character - alpha.head + rotation + alpha.size) % alpha.size)
