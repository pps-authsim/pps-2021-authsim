package it.unibo.authsim.library.dsl.cryptography

import it.unibo.authsim.library.dsl.cryptography.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.symmetric.{AES, DES, CaesarCipher}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CryptographicAlgorithmTest extends AnyFlatSpec with Matchers {
  /*
  val sha1 = new HashFunction.SHA1
  val md5 = new HashFunction.MD5
  val sha256 = new HashFunction.SHA256
  val sha384 = new HashFunction.SHA384
  val des = DES()
  val aes = AES()
  val rsa = RSA()
  val caesarCipher = CaesarCipher()

  "A cryptographic algorithm" should "provide a name" in {
    sha1.algorithmName shouldBe sha1.toString
    sha256.algorithmName shouldBe sha256.toString
    sha384.algorithmName shouldBe sha384.toString
    md5.algorithmName shouldBe md5.toString
    des.algorithmName shouldBe des.toString
    aes.algorithmName shouldBe aes.toString
    rsa.algorithmName shouldBe rsa.toString
  }

   */
}
