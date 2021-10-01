package it.unibo.authsim.library.dsl.cryptography.encrypter

import it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric.{AESEncrypter, CaesarCipherEncrypter, DESEncrypter}
import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.RSAEncrypter

object EncrypterAbstractFactory:
  enum EncryptionAlgorithm:
    case CaesarCipher, AES, DES, RSA

  def apply[A>: Encrypter](name: EncryptionAlgorithm): A =
    name match {
      case EncryptionAlgorithm.CaesarCipher => CaesarCipherEncrypter
      case EncryptionAlgorithm.AES => AESEncrypter
      case EncryptionAlgorithm.DES => DESEncrypter
      case EncryptionAlgorithm.RSA => RSAEncrypter
    }
