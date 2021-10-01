package it.unibo.authsim.library.dsl.cryptography.algorithm

/**
 * Trait that represent an encryption algorithm
 */
trait EncryptionAlgorithm:
  type Salt
  /**
   * Method used to get the name of the crypthographic algorithm
   * @return                        a string representing the name of crypthographic algorithm
   */
  def algorithmName: String
  def salt: Option[Salt]
  def keyLength: Int

trait SymmetricEncryptionAlgorithm extends EncryptionAlgorithm

trait AsymmetricEncryptionAlgorithm extends EncryptionAlgorithm
