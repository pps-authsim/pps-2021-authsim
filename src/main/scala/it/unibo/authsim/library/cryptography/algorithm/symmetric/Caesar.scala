package it.unibo.authsim.library.cryptography.algorithm.symmetric

import it.unibo.authsim.library.cryptography.algorithm.SymmetricAlgorithm
/**
 * Trait for Caesar Cipher algorithm.
 */
trait Caesar extends SymmetricAlgorithm

/**
 * Companion object of the Caesar Cipher trait.
 */
object Caesar:
  /**
   * Apply method for the object.
   * 
   * @return : an istance of the Caesar Cipher case class
   */
  def apply() = new BasicCaesar()

  /**
   * Class representing a basic implementation of the Caesar Cipher algorithm.
   */
  case class BasicCaesar() extends Caesar:
    import it.unibo.authsim.library.cryptography.util.ImplicitConversion._
    
    /**
     * Private variable representing the algorithm name.
     */
    private val _name : String = "CaesarCipher"
    
    /**
     * Private variable representing the length of the key used during the cryptographic operation.
     */
    private val _length : Int = 8 

    /**
     * Getter for the salt value.
     *
     *  @return : None if the algorithm does not use a salt value, or an optional of the salt value used by the algorithm
     */
    override def salt: Option[String] = None
    /**
     * Getter for the key used during the encryption operation.
     *
     *  @return : the size of the key used by the algorithm in bytes
     */
    override def keyLength: Int = _length
    
    /**
     * Getter for the algorithm name.
     *
     *  @return : a string representing the name of crypthographic algorithm
     */
    override def name: String = _name