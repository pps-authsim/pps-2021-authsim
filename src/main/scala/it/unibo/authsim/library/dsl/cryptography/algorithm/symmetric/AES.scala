package it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm


trait AES extends SymmetricEncryptionAlgorithm:
  def keyLength_(newKeyLength:Int):Unit

object AES:
  import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key.KeysGenerator

  def apply()= new BasicAES()

  class BasicAES() extends AES:

    type Salt = String

    private var _length : Int = 16 //byte

    val _name : String ="AES"

    private var keySet = Set(16, 24, 32)

    private var _salt: String = "123456789"

    override def salt: Option[String] = Some(_salt)

    def keyLength_(newKeyLength: Int): Unit=
      if(keySet.contains(newKeyLength)) then
        _length=newKeyLength
      else
        println("invalid key size")

    override def keyLength: Int = _length

    override def algorithmName: String = "AES"
