package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.symmetric.{AES, CaesarCipher, DES}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class SymmetricEncryptionTest extends AnyWordSpec with Matchers {
  val des = DES()
  val aes = AES()
  val caesarCipher= CaesarCipher()
  val rotation:Int=2
  val secret: String = "12345678123456781234567812345678"
  val password: String ="password"

  "DES encryption" should {
    "return a string type" in {
      des.encrypt(password, secret).isInstanceOf[String] shouldBe true
    }

    "be " in {
      des.decrypt( des.encrypt(password, secret), secret).equals(password) shouldBe true
    }
  }

  "AES encryption" should{
      "be " in{
        aes.decrypt(aes.encrypt(password, secret), secret).equals(password) shouldBe true
      }
  }

  "A password encrypted with a Caesar cipher" should{
    "be " in{
      caesarCipher.decrypt( caesarCipher.encrypt(password, rotation), rotation).equals(password) shouldBe true
    }

    "if rotation is 0 then encryption with Caesar cipher should work as identity function" in{
      caesarCipher.encrypt(password, 0).equals(password) shouldBe true
    }

  }

}