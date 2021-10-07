package it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric._

class SymmetricAlgorithm extends AnyWordSpec with Matchers{
  val des= DES()
  val aes= AES()
  val caesar= Caesar()

  "A DES algorithm" should {
    "have a name" in{
      des.algorithmName shouldBe "DES"
    }
    "have a default key's length" in{
      des.keyLength shouldEqual 7
    }
    "have a default salt value" in{
      des.salt shouldEqual None
    }
    "allow to set the salt value" in{
      des.salt_("aaa")
      des.salt.get shouldEqual "aaa"
    }
  }

  "A AES algorithm" should {
    "have a name" in{
      aes.algorithmName shouldBe "AES"
    }
    "have a default key's length" in{
      aes.keyLength shouldEqual 16
    }
    "have a default salt value" in{
      aes.salt shouldEqual None
    }
    "allow to set the salt" in{
      aes.salt_("aaa")
      aes.salt.get shouldEqual ("aaa")
    }
    "have allow key length re-definition"in{
      aes.keyLength_(32)
      aes.keyLength shouldEqual 32
    }
    "do not key length redefinition not complaint with the algorithm rules" in{
      aes.keyLength_(2)
      aes.keyLength shouldEqual 32
    }
  }

  "A Caesar Cipher algorithm" should {
    "have a name" in{
      caesar.algorithmName shouldBe "CaesarCipher"
    }
    "have a default key's length" in{
      caesar.keyLength shouldEqual 8
    }
    "not have a salt value" in{
      caesar.salt shouldEqual None
    }
  }
}
