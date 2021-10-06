package it.unibo.authsim.library.dsl.cryptography.algorithm

/**
 * Trait that represent an encryption algorithm
 */
trait EncryptionAlgorithm extends CryptographicAlgorithm:
  /**
   * Getter for the length of the key used by algorithm
   * 
   * @return                        the size of the used by the algorithm
   */
  def keyLength: Int


/**
 * Trait that represent symmetric encryption algorithms
 */
trait SymmetricEncryptionAlgorithm extends EncryptionAlgorithm

/**
 * Trait that represent asymmetric encryption algorithms
 */
trait AsymmetricEncryptionAlgorithm extends EncryptionAlgorithm


