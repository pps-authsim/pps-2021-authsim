package it.unibo.authsim.library.dsl.cryptography.algorithm.hash

import com.google.common.hash.Hashing
import com.google.common.io.BaseEncoding
import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

import java.nio.charset.StandardCharsets

trait HashFunction extends CryptographicAlgorithm:
  def hash(str: String): String
  def _salt[A](salt:A): Unit

object HashFunction:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  abstract class BasicHash extends HashFunction:
    protected var _salt: String= ""

    override def salt: Option[String]=Option(_salt)

    override def _salt[A](salt:A): Unit= _salt=salt

  case class SHA1() extends BasicHash:
    override def algorithmName: String = this.toString

    override def hash(password: String): String = DigestUtils.shaHex(password.concat(_salt).getBytes(StandardCharsets.UTF_8))
  
  case class SHA256() extends BasicHash:
    override def algorithmName: String = this.toString
    override def hash(password: String): String = Hashing.sha256().hashString(password.concat(_salt), StandardCharsets.UTF_8).toString
  
  case class SHA384() extends BasicHash:
    override def algorithmName: String = this.toString
    override def hash(password: String): String = Hashing.sha384().hashString(password.concat(_salt), StandardCharsets.UTF_8).toString
  
  case class MD5() extends BasicHash:
    override def algorithmName: String = this.toString
    override def hash(password: String): String = DigestUtils.md5Hex(password.concat(_salt).getBytes(StandardCharsets.UTF_8))