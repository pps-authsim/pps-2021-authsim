package it.unibo.authsim.library.dsl.cryptography.hash

import com.google.common.hash.Hashing
import com.google.common.io.BaseEncoding
import it.unibo.authsim.library.dsl.cryptography.CryptographicAlgorithm

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

import java.nio.charset.StandardCharsets

trait HashFunction extends CryptographicAlgorithm:
  def hash(str: String): String

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