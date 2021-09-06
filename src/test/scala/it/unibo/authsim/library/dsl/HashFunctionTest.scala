package it.unibo.authsim.library.dsl

import it.unibo.authsim.library.dsl.HashFunction
import org.scalatest.wordspec.AnyWordSpec

class HashFunctionTest extends AnyWordSpec {
  var password = "foo"
  var hashFunctionSHA1 = new HashFunction.SHA1
  var hashFunctionMD5 = new HashFunction.MD5
  var hashFunctionSHA256 = new HashFunction.SHA256
  var hashFunctionSHA384 = new HashFunction.SHA384
  var hashedValueSHA1 = "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33"
  var hashedValueSHA256 = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae"
  var hashedValueSHA384 = "98c11ffdfdd540676b1a137cb1a22b2a70350c9a44171d6b1180c6be5cbb2ee3f79d532c8a1dd9ef2e8e08e752a3babb"
  var hashedValueMD5 = "acbd18db4cc2f85cedef654fccc4a4d8"
  def is = afterWord("is")

  "A password" when {
    "hashed with the SHA1 algorithm" should{
      "return the hashed value" which is {
        assert(hashFunctionSHA1.hash(password) == hashedValueSHA1)
      }
      "and is not " in{
        assert(hashFunctionSHA1.hash(password) != "")
      }
    }
  }

  "A password" when {
    "hashed with the SHA256 algorithm" should{
      "return the hashed value" in {
        assert(hashFunctionSHA256.hash(password) == hashedValueSHA256)
      }
      "and is not " in{
        assert(hashFunctionSHA256.hash(password) != "")
      }
    }
  }

  "A password" when {
    "hashed with the SHA384 algorithm" should{
      "return the hashed value" in {
        assert(hashFunctionSHA384.hash(password) == hashedValueSHA384)
      }
      "and is not " in{
        assert(hashFunctionSHA384.hash(password) != "")
      }
    }
  }

  "A password" when {
    "hashed with MD5 algorithm" should {
      "return the hashed value" in {
        assert(hashFunctionMD5.hash(password) == hashedValueMD5)
      }
      "and is not " in{
        assert(hashFunctionMD5.hash(password) != "")
      }
    }
  }

}
