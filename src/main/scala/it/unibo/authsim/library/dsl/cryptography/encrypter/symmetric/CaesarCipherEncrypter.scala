package it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.CaesarCipher
import it.unibo.authsim.library.dsl.cryptography.encrypter.Encrypter
import sun.security.util.Password

object CaesarCipherEncrypter extends Encrypter:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._
  var algorithm : CaesarCipher = CaesarCipher()
  override def encrypt[A,B](password: A, rotation: B): String = crypto(password, rotation)

  override def decrypt[A,B](password: A, rotation: B): String = crypto(password,-rotation)

  private def crypto(password: String, rotation:Int)=
    password.toLowerCase.map {
      case character
        if algorithm.alphabet.contains(character) =>
        shift(algorithm.alphabet, character, rotation)
      case character => character
    }

  private def shift(alpha:IndexedSeq[Char], character:Char, rotation:Int)=
    alpha((character - alpha.head + rotation + alpha.size) % alpha.size)
