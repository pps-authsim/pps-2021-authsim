package it.unibo.authsim.library.cryptography.algorithm.symmetric

import it.unibo.authsim.library.cryptography.algorithm.SymmetricAlgorithm

/**
 * Trait for DES algorithm.
 */
trait DES extends SymmetricAlgorithm:
  /**
   * Setter for the salt value.
   *
   * @param salt : new value for the salt
   * @tparam A : type of the value
   */
  def salt_[A](salt: A):Unit
  
/**
 * Companion object of the DES trait
 */
object DES:
  import it.unibo.authsim.library.cryptography.cipher.asymmetric.key.KeysGenerator
  import it.unibo.authsim.library.cryptography.util.ImplicitConversion.ImplicitConversionToBuiltinType._
  /**
   * Apply method for the object
   * 
   * @return an istance of the DES case class
   */
  def apply()= new BasicDES()
  
  /**
   * Class representing a basic implementation of the DES algorithm
   */
  case class BasicDES() extends DES:
    /**
     * Private variable representing the length of the key used by the algorithm.
     */
    private var _length : Int = 7
    
    /**
     * Private variable representing the algorithm name.
     */
    private val _name : String ="DES"

    /**
     * Private variable representing the salt value.
     */
    private var _salt: Option[String] = None

    /**
     * Setter for the salt value.
     *
     * @param salt  new value for the salt
     * @tparam A  type of the value
     */
    override def salt_[A](salt:A): Unit = _salt=Some(salt)

    /**
     * Getter for the salt value.
     *
     *  @return : None if the algorithm does not use a salt value, or an optional of the salt value used by the algorithm
     */
    override def salt: Option[String] = _salt

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
