package it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.AsymmetricEncryptionAlgorithm

trait RSA extends AsymmetricEncryptionAlgorithm:
  def keyLength_(newKeyLength:Int):Unit

object RSA:
  import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key.KeysGenerator

  def apply()= new BasicRSA()

  class BasicRSA() extends RSA:
    type Salt = String

    private val _name = "RSA"

    private var keySet = Set(1024, 2048, 4096)

    private var _length= 2048//bit

    override def keyLength: Int = _length

    override def algorithmName: String = "RSA"

    override def salt: Option[String] = None

    override def keyLength_(newKeyLength:Int):Unit=
      if(keySet.contains(newKeyLength)) then
        _length=newKeyLength
      else
        println("invalid key size")