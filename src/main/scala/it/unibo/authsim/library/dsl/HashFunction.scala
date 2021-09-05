package it.unibo.authsim.library.dsl

import java.nio.charset.StandardCharsets
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

trait HashFunction {
  def hash(str: String): String
}

object HashFunction {
  case class SHA() extends HashFunction {
    override def hash(str: String): String = DigestUtils.shaHex(str.getBytes(StandardCharsets.UTF_8))
  }
  case class MD5() extends HashFunction {
    override def hash(str: String): String = DigestUtils.md5Hex(str.getBytes(StandardCharsets.UTF_8))
  }
}
