package it.unibo.authsim.library.dsl.cryptography.encrypter

import it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric.{AESEncrypter, CaesarCipherEncrypter, DESEncrypter}
import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.RSAEncrypter

/**
 * Abstract factory for building encryption algorithm
 */
object EncrypterAbstractFactory:
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
  def apply[A>: Encrypter](name: EncryptionAlgorithm): A =            //TODO: capire se riesce a passare il tipo effettivo: A.type non funziona
    name match {
      case EncryptionAlgorithm.CaesarCipher => CaesarCipherEncrypter()
      case EncryptionAlgorithm.AES => AESEncrypter()
      case EncryptionAlgorithm.DES => DESEncrypter()
      case EncryptionAlgorithm.RSA => RSAEncrypter()
    }
