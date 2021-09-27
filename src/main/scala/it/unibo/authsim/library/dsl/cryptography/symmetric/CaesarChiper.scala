package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.symmetric.SymmetricEncryption
import sun.security.util.Password

trait CaesarCipher extends SymmetricEncryption

object CaesarCipher:
  def apply() = new CaesarCipher():
    private val alphaL=('a' to 'z') ++ ('A' to 'Z')

    private def crypto(password: String, key:Int)=
      password.map{
        case c if alphaL.contains(c) => shift(alphaL, c, key)
        case c => c
      }
    override def toString: String = "CaesarCipher"

    override def encrypt(password: String, key: String): String = crypto(password.toLowerCase, key.toInt)

    override def decrypt(password: String, key: String): String = crypto(password.toLowerCase,-key.toInt)

    override def algorithmName: String = this.toString

    private def shift(a:IndexedSeq[Char], c:Char, key:Int)=a((c-a.head+key+a.size)%a.size)