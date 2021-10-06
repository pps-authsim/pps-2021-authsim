package it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.AsymmetricEncryptionAlgorithm

/**
 * Trait for RSA algorithm
 */
trait RSA extends AsymmetricEncryptionAlgorithm:
  /**
   * Setter for the length of the key to be used in the encryption operation.
   *
   * @param newKeyLength          new key length
   */
  def keyLength_(newKeyLength:Int):Unit

/**
 * Companion object of the AES trait
 */
object RSA:
  import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key.KeysGenerator

  /**
   * Apply method for the object
   * @return an istance of the RSA case class
   */
  def apply()= new BasicRSA()

  /**
   * Class representing a basic implementation of the RSA algorithm
   */
  case class BasicRSA() extends RSA:
    /**
     * Private variable representing the algorithm name
     */
    private val _name = "RSA"

    /**
     * Private variable representing the length of the key algorithm supports
     */
    private val keySet = Set(1024, 2048, 4096)

    /**
     * Private variable representing the length of the key used during the cryptographic operation
     */
    private var _length= 2048//bit

    /**
     * Getter for the key used during the encryption operation
     *
     *  @return                        the size of the key used by the algorithm in bit
     */
    override def keyLength: Int = _length

    /**
     * Getter for the algorithm name
     *
     *  @return                 a string representing the name of crypthographic algorithm
     */
    override def algorithmName: String = _name

    /**
     * Setter for new key length; it prints a log error if the key length is not supported by the algorithm
     * 
     * @param newKeyLength          new key length
     */
    override def keyLength_(newKeyLength:Int):Unit=
      if(keySet.contains(newKeyLength)) then
        _length=newKeyLength
      else
        println("invalid key size")