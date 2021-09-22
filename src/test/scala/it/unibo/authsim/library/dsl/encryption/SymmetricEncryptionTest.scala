package it.unibo.authsim.library.dsl.encryption

import it.unibo.authsim.library.dsl.encryption.symmetric.{AES, DES}
import it.unibo.authsim.library.dsl.encryption.util.Util.toMultiple
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers


class SymmetricEncryptionTest extends AnyWordSpec with Matchers {
  val des = DES()
  val secret: String = "12345678123456781234567812345678"
  val password: String ="password"
  val passwordEncryptedDES: String ="UhcmgOGbLqg1vi4OK4cDeA=="
  val passwordEncryptedAES: String ="8h+0CcFUODz2Im9juBffCw=="
  val aes = AES()
  "DES encryption" should {
    /*"return a string type" in {
      des.encrypt(password, secret).isInstanceOf[String] shouldBe true
    }
     */
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
}