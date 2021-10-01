package it.unibo.authsim.library.dsl.cryptography.algorithm

/**
 * Trait that represent an encryption algorithm
 */
trait EncryptionAlgorithm:
  /**
   * Type value for the salt
   */
  type Salt
  /**
   * Getter for the name of the crypthographic algorithm
   * @return                        a string representing the name of crypthographic algorithm
   */
  def algorithmName: String

  /**
   * Getter for the salt used by the algorithm 
   * 
   * @return                        None if the algorithm does not use a salt value, or an optional of the salt value used by the algorithm
   */
  def salt: Option[Salt]

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
