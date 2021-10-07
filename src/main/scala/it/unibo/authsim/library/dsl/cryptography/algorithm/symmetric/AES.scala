package it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm

/**
 * Trait for AES algorithm
 */
trait AES extends SymmetricEncryptionAlgorithm:
  /**
   * Setter for the length of the key to be used in the encryption operation.
   *
   * @param newKeyLength          new key length
   */
  def keyLength_(newKeyLength:Int):Unit

  def salt_[A](salt: A):Unit

/**
 * Companion object of the AES trait
 */
object AES:
  import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.key.KeysGenerator
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  /**
   * Apply method for the object
   * @return an istance of the AES case class
   */
  def apply()= new BasicAES()

  /**
   * Class representing a basic implementation of the AES algorithm
   */
  case class BasicAES() extends AES:
    
    /**
     * Private variable representing the length of the key used during the cryptographic operation
     */
    private var _length : Int = 16 
    
    /**
     * Private variable representing the algorithm name
     */
    private val _name : String ="AES"

    /**
     * Private variable representing the length of the key algorithm supports
     */
    private val keySet = Set(16, 24, 32)

    /**
     * Private variable representing the salt value
     */
    private var _salt: Option[String] = None

    /**
     * Setter for the salt value
     *
     * @param salt                    new value for the salt
     * @tparam A                      type of the value
     */
    override def salt_[A](salt:A): Unit = _salt = Some(salt)

    /**
     * Getter for the salt value
     *
     *  @return                        None if the algorithm does not use a salt value, or an optional of the salt value used by the algorithm
     */
    override def salt: Option[String] = _salt

    /**
     * Setter for the length of the key to be used in the encryption operation.
     * The new key dimension must be complaint with the original algorithm specification
     * if not the default value cannot be overriden
     *
     * @param newKeyLength          new key length must be 16, 24 32 (bytes)
     */
    def keyLength_(newKeyLength: Int): Unit=
      if(keySet.contains(newKeyLength)) then
        _length=newKeyLength
      else
        println("invalid key size")

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