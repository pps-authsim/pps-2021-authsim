package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.symmetric.SymmetricEncryption
import sun.security.util.Password

trait CaesarCipher extends SymmetricEncryption

object CaesarCipher:
  def apply() = new CaesarCipher():
    import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

    private var alpha=('a' to 'z') ++ ('1' to '9')

    override def toString: String = "CaesarCipher"

    override def encrypt[A,B](password: A, rotation: B): String = crypto(password, rotation)

    override def decrypt[A,B](password: A, rotation: B): String = crypto(password,-rotation)

    private def crypto(password: String, rotation:Int)=
      password.toLowerCase.map {
        case character if alpha.contains(character) => shift(alpha, character, rotation)
        case character => character
      }

    override def algorithmName: String = this.toString

    private def shift(alpha:IndexedSeq[Char], character:Char, rotation:Int)=
      alpha((character - alpha.head + rotation + alpha.size) % alpha.size)
