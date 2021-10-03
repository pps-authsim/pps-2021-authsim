package it.unibo.authsim.library.dsl.cryptography.algorithm.hash

import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HashFunctionTest extends AnyWordSpec with Matchers {
  val password = "foo"
  val sha1 = new HashFunction.SHA1
  val md5 = new HashFunction.MD5
  val sha256 = new HashFunction.SHA256
  val sha384 = new HashFunction.SHA384
  val hashedValueSHA1 = "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33"
  val hashedValueSHA256 = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae"
  val hashedValueSHA384 = "98c11ffdfdd540676b1a137cb1a22b2a70350c9a44171d6b1180c6be5cbb2ee3f79d532c8a1dd9ef2e8e08e752a3babb"
  val hashedValueMD5 = "acbd18db4cc2f85cedef654fccc4a4d8"
  def is = afterWord("is")

  "A password" when {
    "hashed with the SHA1 algorithm" should{
      "return the hashed value" which is {
        sha1.hash(password) shouldBe hashedValueSHA1
      }
      "and is not " in{
        assert(sha1.hash(password) != "")
      }
    }
  }

  "A password" when {
    "hashed with the SHA256 algorithm" should{
      "return the hashed value" in {
        sha256.hash(password) shouldBe hashedValueSHA256
      }
      "and is not " in{
        assert(sha256.hash(password) != "")
      }
    }
  }

  "A password" when {
    "hashed with the SHA384 algorithm" should{
      "return the hashed value" in {
        sha384.hash(password) shouldBe hashedValueSHA384
      }
      "and is not " in{
        assert(sha384.hash(password) != "")
      }
    }
  }

  "A password" when {
    "hashed with MD5 algorithm" should {
      "return the hashed value" in {
        md5.hash(password) shouldBe hashedValueMD5
      }
      "and is not " in{
        assert(md5.hash(password) != "")
      }
    }
  }
}
