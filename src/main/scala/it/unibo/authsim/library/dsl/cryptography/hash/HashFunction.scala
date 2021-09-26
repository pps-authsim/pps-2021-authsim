package it.unibo.authsim.library.dsl.cryptography.hash

import com.google.common.hash.Hashing
import com.google.common.io.BaseEncoding
import it.unibo.authsim.library.dsl.cryptography.HashFunction
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

import java.nio.charset.StandardCharsets

object HashFunction:
  case class SHA1() extends HashFunction:
    override def algorithmName: String = this.toString

    override def hash(password: String): String = DigestUtils.shaHex(password.getBytes(StandardCharsets.UTF_8))
  
  case class SHA256() extends HashFunction:
    override def algorithmName: String = this.toString
    override def hash(password: String): String = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString
  
  case class SHA384() extends HashFunction:
    override def algorithmName: String = this.toString
    override def hash(password: String): String = Hashing.sha384().hashString(password, StandardCharsets.UTF_8).toString
  
  case class MD5() extends HashFunction:
    override def algorithmName: String = this.toString

    override def hash(password: String): String = DigestUtils.md5Hex(password.getBytes(StandardCharsets.UTF_8))

object App1:
  def main(args: Array[String]): Unit =
    val md5= HashFunction.MD5
    val sha384= HashFunction.SHA384
    val sha256= HashFunction.SHA256
    val sha1= HashFunction.SHA1

    println("to string"+ (sha1, sha256, sha384, md5))
