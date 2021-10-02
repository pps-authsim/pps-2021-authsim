package it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm
/**
 * Trait for Caesar Cipher algorithm
 */
trait CaesarCipher extends SymmetricEncryptionAlgorithm:
  /**
   * Getter for tha alphabet to be used during the cryptographic operations
   * @return    IndexedSeq[Char] representing the algorithm used
   */
  def alphabet: IndexedSeq[Char]
  
/**
 * Companion object of the Caesar Cipher trait
 */
object CaesarCipher:
  /**
   * Apply method for the object
   * @return an istance of the Caesar Cipher case class
   */
  def apply() = new CaesarCipherImpl()
  
  /**
   * Class representing a basic implementation of the Caesar Cipher algorithm
   */
  case class CaesarCipherImpl() extends CaesarCipher:
    import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._
    
    type Salt = String
    
    /**
     * Private variable representing the algorithm name
     */
    private val _name : String = "CaesarCipher"
    
    /**
     * Private variable representing the length of the key used during the cryptographic operation
     */
    private val _length : Int = 8 //byte int length
    /**
     * Private variable representing the alphabet used during the cryptographic operation
     */
    private var _alphabet=('a' to 'z') ++ ('1' to '9')
    
    /**
     * Getter for the salt value
     *
     *  @return                        None if the algorithm does not use a salt value, or an optional of the salt value used by the algorithm
     */
    override def salt: Option[String] = None
    /**
     * Getter for the key used during the encryption operation
     *
     *  @return                        the size of the key used by the algorithm in bytes
     */
    override def keyLength: Int = _length
    
    /**
     * Getter for the algorithm name
     *
     *  @return                 a string representing the name of crypthographic algorithm
     */
    override def algorithmName: String = _name

    /**
     * Getter for the alphabet
     * 
     *  @return    IndexedSeq[Char] representing the algorithm used
     */
    override def alphabet: IndexedSeq[Char] = _alphabet