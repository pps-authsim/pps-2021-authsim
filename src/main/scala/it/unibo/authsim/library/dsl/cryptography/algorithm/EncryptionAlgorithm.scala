package it.unibo.authsim.library.dsl.cryptography.algorithm

/**
 * Trait that represents an encryption algorithm.
 */
trait EncryptionAlgorithm extends CryptographicAlgorithm:
  /**
   * Getter for the length of the key used by algorithm.
   * 
   * @return the size of the used by the algorithm
   */
  def keyLength: Int


/**
 * Trait that represents symmetric encryption algorithms.
 */
trait SymmetricAlgorithm extends EncryptionAlgorithm

/**
 * Trait that represents asymmetric encryption algorithms.
 */
trait AsymmetricAlgorithm extends EncryptionAlgorithm


