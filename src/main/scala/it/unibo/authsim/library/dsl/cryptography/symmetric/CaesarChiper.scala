package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.symmetric.SymmetricEncryption
import sun.security.util.Password

trait CaesarCipher extends SymmetricEncryption

object CaesarCipher:
  def apply() = new CaesarCipher():
    import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion.{objectToString, objectToInt}

    private var alphaL=('a' to 'z') ++ ('A' to 'Z') 

    private def crypto(password: String, key:Int)=
      password.map{
        case c if alphaL.contains(c) => shift(alphaL, c, key)
        case c => c
      }

    def alphabet_(newAlphabet:String) : Unit = alphaL = newAlphabet

    override def toString: String = "CaesarCipher"

    override def encrypt[A,B](password: A, key: B): String = crypto(password.toLowerCase, key)

    override def decrypt[A,B](password: A, key: B): String = crypto(password.toLowerCase,-key)

    override def algorithmName: String = this.toString

    private def shift(a:IndexedSeq[Char], c:Char, key:Int)=a((c-a.head+key+a.size)%a.size)