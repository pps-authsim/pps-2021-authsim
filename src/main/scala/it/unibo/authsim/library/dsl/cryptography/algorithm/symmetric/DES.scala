package it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm


trait DES extends SymmetricEncryptionAlgorithm:
  def salt_[A](salt: A):Unit

object DES:
  import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key.KeysGenerator
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  def apply(): DES = new BasicDES()

  private case class BasicDES() extends DES:

    private var _length : Int = 7 //bit or 64
    
    /**
     * Private variable representing the algorithm name
     */
    val _name : String ="DES"
//TODO generate random value
    /**
     * Private variable representing the salt value
     */
    private var _salt: String = "aaaa"
      //Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte], 0xC8.asInstanceOf[Byte], 0x32.asInstanceOf[Byte], 0x56.asInstanceOf[Byte], 0x35.asInstanceOf[Byte], 0xE3.asInstanceOf[Byte], 0x03.asInstanceOf[Byte])

    /**
     * Setter for the salt value
     *
     * @param salt                    new value for the salt
     * @tparam A                      type of the value
     */
    override def salt_[A](salt:A): Unit = _salt=salt

    /**
     * Getter for the salt value
     *
     *  @return                        None if the algorithm does not use a salt value, or an optional of the salt value used by the algorithm
     */
    override def salt: Option[String] = Some(_salt)
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
