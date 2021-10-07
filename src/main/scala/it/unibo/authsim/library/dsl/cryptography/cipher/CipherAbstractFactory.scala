package it.unibo.authsim.library.dsl.cryptography.cipher

import it.unibo.authsim.library.dsl.cryptography.cipher.symmetric.{AESCipher, CaesarCipher, DESCipher}
import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.RSACipher
object CipherFactory:
  /**
   * Abstract factory for building encryption algorithm
   */
  object SymmetricCipherAbstractFactory:

    /**
     * Apply method for the abstract encryption factory
     * @param name        name of the encryption algorithm one wants to create
     * @tparam A          type of the algorithm
     * @return            an istance of the algorithm chosen
     */
    def apply[A>: SymmetricCipher](name: String): A =
      name.toLowerCase match {
        case "caesar" => CaesarCipher()
        case "aes" => AESCipher()
        case "des" => DESCipher()
      }

  /**
   * Abstract factory for building encryption algorithm
   */
  object AsymmetricCipherAbstractFactory:

    /**
     * Apply method for the abstract encryption factory
     * @param name        name of the encryption algorithm one wants to create
     * @tparam A          type of the algorithm
     * @return            an istance of the algorithm chosen
     */
    def apply[A>: AsymmetricCipher](name: String): A =
      name.toLowerCase match {
        case "rsa" => RSACipher()
      }


