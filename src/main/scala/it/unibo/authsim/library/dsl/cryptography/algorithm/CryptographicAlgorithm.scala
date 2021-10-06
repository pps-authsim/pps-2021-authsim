package it.unibo.authsim.library.dsl.cryptography.algorithm

/**
 * Trait that represent all the cryptographic algorithms: hash, symmetric and asymmetric
 */
trait CryptographicAlgorithm:
  /**
   * Getter for the name of the crypthographic algorithm
   * @return                 a string representing the name of crypthographic algorithm
   */
  def algorithmName: String

  /**
   * Getter for the salt used by the algorithm
   *
   * @return                        None if the algorithm does not use a salt value, or an optional of the salt value used by the algorithm
   */
  def salt: Option[String]= None