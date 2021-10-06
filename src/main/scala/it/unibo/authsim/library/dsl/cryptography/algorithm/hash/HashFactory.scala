package it.unibo.authsim.library.dsl.cryptography.algorithm.hash
/**
 * Abstract factory for building hash algorithm
 */
object HashAbstractFactory:
  /**
   * Enumeration who provide the name of the hash algorithms supported
   */
  enum HashFunctionAlgorithm:
    case MD5, SHA1, SHA256, SHA384

  def apply[A>: HashFunction](name: HashFunctionAlgorithm): A=
  /**
   * Apply method for the abstract hash factory
   * @param name        name of the hash algorithm one wants to create
   * @tparam A          type of the algorithm
   * @return            an istance of the algorithm chosen
   */
    name match {
      case HashFunctionAlgorithm.MD5 => new HashFunction.MD5
      case HashFunctionAlgorithm.SHA1=> new HashFunction.SHA1
      case HashFunctionAlgorithm.SHA256 => new HashFunction.SHA256
      case HashFunctionAlgorithm.SHA384 => new HashFunction.SHA384
    }
