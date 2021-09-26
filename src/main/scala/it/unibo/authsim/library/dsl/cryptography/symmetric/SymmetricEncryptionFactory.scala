package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.{CryptographicAlgorithm, Encryption}

trait SymmetricEncryption extends Encryption with CryptographicAlgorithm

enum SymmetricEncryptionAlgorithm:
  case CaesarCipher, AES, DES

object SymmetricEncryptionAbstractFactory:
  def apply[A>: SymmetricEncryption ](name: SymmetricEncryptionAlgorithm): A =
    name match {
      case SymmetricEncryptionAlgorithm.CaesarCipher => CaesarCipher()
      case SymmetricEncryptionAlgorithm.AES => AES()
      case SymmetricEncryptionAlgorithm.DES => DES()
    }