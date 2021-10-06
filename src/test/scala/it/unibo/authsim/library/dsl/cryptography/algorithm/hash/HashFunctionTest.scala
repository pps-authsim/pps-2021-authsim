package it.unibo.authsim.library.dsl.cryptography.algorithm.hash

import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

class HashFunctionTest extends AnyWordSpec with Matchers with BeforeAndAfter {
  private val password = "foo"
  private val sha1 = new HashFunction.SHA1
  private val md5 = new HashFunction.MD5
  private val sha256 = new HashFunction.SHA256
  private val sha384 = new HashFunction.SHA384
  private val hashedValueSHA1 = "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33"
  private val hashedValueSHA256 = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae"
  private val hashedValueSHA384 = "98c11ffdfdd540676b1a137cb1a22b2a70350c9a44171d6b1180c6be5cbb2ee3f79d532c8a1dd9ef2e8e08e752a3babb"
  private val hashedValueMD5 = "acbd18db4cc2f85cedef654fccc4a4d8"
  private var salt:String= ""
  private val minLength=5
  private val maxLength=20
  def is = afterWord("is")

  
  "A password" when {
    "hashed with the SHA1 algorithm" should{
      "return the hashed value" which is {
        sha1.hash(password) shouldBe hashedValueSHA1
      }
      "password could also be salted, in this case it" should {
        "the hashed value should be different" in {
          sha1.salt_(salt)
          sha1.salt.get should be(salt)
          sha1.hash(password) should not be hashedValueSHA1
        }
      }
    }
  }

  "A password" when {
    "hashed with the SHA256 algorithm" should {
      "return the hashed value" in {
        sha256.hash(password) shouldBe hashedValueSHA256
      }
    }
      "password could also be salted, in this case it" should{
        "the hashed value should be different" in{
          sha256.salt_(salt)
          sha256.salt.get should be (salt)
          sha256.hash(password) should not be hashedValueSHA256
        }
      }
  }

  "A password" when {
    "hashed with the SHA384 algorithm" should{
      "return the hashed value" in {
        sha384.hash(password) shouldBe hashedValueSHA384
      }
      "password could also be salted, in this case it" should {
        "the hashed value should be different" in {
          sha384.salt_(salt)
          sha384.salt.get should be(salt)
          sha384.hash(password) should not be hashedValueSHA384
        }
      }
    }
  }

  "A password" when {
    "hashed with MD5 algorithm" should {
      "return the hashed value" in {
        md5.hash(password) shouldBe hashedValueMD5
      }
      "password could also be salted, in this case it" should {
        "the hashed value should be different" in {
          md5.salt_(salt)
          md5.salt.get should be(salt)
          md5.hash(password) should not be hashedValueMD5
        }
      }
    }
  }

  before {
   salt= Random.alphanumeric.filter(_.isLetterOrDigit).take(Random.between(minLength,maxLength)).mkString
  }
}
