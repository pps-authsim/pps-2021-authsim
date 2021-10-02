package it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric

import it.unibo.authsim.library.dsl.cryptography.util.DiskManager
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.apache.commons.lang3.RandomStringUtils

import scala.util.Random


class SymmetricEncryptionTest extends AnyWordSpec with Matchers with BeforeAndAfter {
  val des = DESCipher()
  val aes = AESCipher()
  val caesarCipher= CaesarCipherCipher()
  val rotation:Int = 2
  val secret: String = "12345678123456781234567812345678"
  //val password: String ="password"
  var passwordSet: List[String]= List.empty[String]
  "DES encryption" should {
    "return a string type" in {
      passwordSet.foreach(password=>des.encrypt(password, secret).isInstanceOf[String] shouldBe true)
    }

    "be " in {
      passwordSet.foreach(password=>des.decrypt( des.encrypt(password, secret), secret).equals(password) shouldBe true)

    }
  }

  "AES encryption" should{
      "be " in{
        passwordSet.foreach(password=>aes.decrypt(aes.encrypt(password, secret), secret).equals(password) shouldBe true)
      }
  }

  "A password encrypted with a Caesar cipher" should{

    "be " in{
      passwordSet.foreach(password=>caesarCipher.decrypt(caesarCipher.encrypt(password, secret), secret).equals(password) shouldBe true)

    }

    "if rotation is 0 then encryption with Caesar cipher should work as identity function" in{
      passwordSet.foreach(password=>password.map(c => (0 + c).toChar).equals(password) shouldBe true)
    }

  }
  before {
    passwordSet=List.fill(10)(Random.alphanumeric.filter(_.isLetterOrDigit).take(8).mkString)
  }
}
