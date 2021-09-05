package it.unibo.authsim.library.dsl

import com.google.common.hash.Hashing
import com.google.common.io.BaseEncoding
import it.HashFunction

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import scala.util.Try

trait HashFunction {
  //Marica non ha messo nulla in input ma come faccio ad hashare un valore che non ho?
  def hash(str: String): String
}
object HashFunction {
  case class SHA() extends HashFunction {
    override def hash(str: String): String = Hashing.sha256().hashString(str, StandardCharsets.UTF_8).toString
  }
  case class MD5() extends HashFunction{
    override def hash(str: String): String = {
      Hashing.md5().hashString(str, StandardCharsets.UTF_8).toString
    }
  }
}
