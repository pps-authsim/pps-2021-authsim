package it.unibo.authsim.library.dsl.cryptography.encrypter

import it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric.{AESCipher, CaesarCipherCipher, DESCipher}
import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.RSACipher

/**
 * Abstract factory for building encryption algorithm
 */
object CipherAbstractFactory:
  /**
   * Enumeration who provide the name of the encryption algorithms supported
   */
  enum EncryptionAlgorithm:
    case CaesarCipher, AES, DES, RSA

  /**
   * Apply method for the abstract encryption factory
   * @param name        name of the encryption algorithm one wants to create
   * @tparam A          type of the algorithm
   * @return            an istance of the algorithm chosen
   */
  def apply[A>: Cipher](name: EncryptionAlgorithm): A =            //TODO: capire se riesce a passare il tipo effettivo: A.type non funziona
    name match {
      case EncryptionAlgorithm.CaesarCipher => CaesarCipherCipher()
      case EncryptionAlgorithm.AES => AESCipher()
      case EncryptionAlgorithm.DES => DESCipher()
      case EncryptionAlgorithm.RSA => RSACipher()
    }
