package it.unibo.authsim.library.dsl.cryptography

import it.unibo.authsim.library.dsl.cryptography.symmetric.{AES, CaesarCipher, DES}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers


class SymmetricEncryptionTest extends AnyWordSpec with Matchers {
  val des = DES()
  val aes = AES()
  val caesarCipher= CaesarCipher()
  val rotation:String="2"
  val secret: String = "12345678123456781234567812345678"
  val password: String ="password"
  val passwordEncryptedDES: String ="UhcmgOGbLqg1vi4OK4cDeA=="
  val passwordEncryptedAES: String ="8h+0CcFUODz2Im9juBffCw=="
  val passwordEncryptedCaesarCipher: String ="rcuuyqtf"

  "DES encryption" should {
    "return a string type" in {
      des.encrypt(password, secret).isInstanceOf[String] shouldBe true
    }

    "be " in {
      des.encrypt(password, secret).equals(passwordEncryptedDES) shouldBe true
    }
    "that should be equal to the result of the decryption operation" in {
      des.decrypt(passwordEncryptedDES, secret).equals(password) shouldBe true
    }
  }
  "AES encryption" should{
      "be " in{
        aes.encrypt(password, secret).equals(passwordEncryptedAES) shouldBe true
      }
      "that should be equal to the result of the decryption operation" in{
        aes.decrypt(passwordEncryptedAES, secret).equals(password) shouldBe true
      }
  }
  
  "A password encrypted with a Caesar cipher" should{
    "be " in{
      caesarCipher.encrypt(password, rotation).equals(passwordEncryptedCaesarCipher) shouldBe true
    }

    "that should be equal to the result of the decryption operation" in{
      caesarCipher.decrypt(passwordEncryptedCaesarCipher, rotation).equals(password) shouldBe true
    }

    "if rotation is 0 then encryption with Caesar cipher should work as identity function" in{
      caesarCipher.encrypt(password, "0").equals(password) shouldBe true
    }

  }
    
}