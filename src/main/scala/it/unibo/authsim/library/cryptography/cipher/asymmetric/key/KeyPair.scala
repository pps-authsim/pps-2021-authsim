package it.unibo.authsim.library.cryptography.cipher.asymmetric.key

/**
 * Triat that represent a pair of Key to be used for encryption and decription tasks
 */
trait KeyPair:
  /**
   * Getter for the public key
   * @return          the public key encoded as string
   */
  def publicKey: String
  
  /**
   * Getter for the private key
   * 
   * @return          the private key encoded as string
   */
  def privateKey: String
