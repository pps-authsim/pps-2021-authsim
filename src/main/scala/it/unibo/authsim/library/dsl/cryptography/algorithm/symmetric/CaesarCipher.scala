package it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm

trait CaesarCipher extends SymmetricEncryptionAlgorithm:
  def alphabet: IndexedSeq[Char]

object CaesarCipher:
  def apply() = new CaesarCipher():
    import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._
    type Salt = String
    private val _name : String = "CaesarCipher"
    private val _length : Int = 56 //8 bit inte
    private var _alphabet=('a' to 'z') ++ ('1' to '9')

    //override def toString: String = _name

    override def salt: Option[String] = None

    override def keyLength: Int = _length

    override def algorithmName: String = "CaesarCipher"

    override def alphabet: IndexedSeq[Char] = _alphabet