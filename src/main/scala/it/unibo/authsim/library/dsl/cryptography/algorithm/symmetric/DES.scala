package it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm


trait DES extends SymmetricEncryptionAlgorithm

object DES:
  import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key.KeysGenerator
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  def apply()= new BasicDES()

  class BasicDES() extends DES:

    type Salt = String

    private var _length : Int = 64 //bit 7

    val _name : String ="DES"

    private var _salt: Array[Byte] = Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte], 0xC8.asInstanceOf[Byte], 0x32.asInstanceOf[Byte], 0x56.asInstanceOf[Byte], 0x35.asInstanceOf[Byte], 0xE3.asInstanceOf[Byte], 0x03.asInstanceOf[Byte])

    override def salt: Option[String] = Some(_salt)

    override def keyLength: Int = _length

    override def algorithmName: String = "AES"
