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
  val caesarCipher= CaesarCipher()
  var rotation=0
  var salt=""
  var passwordList: List[String]= List.empty[String]
  var secretList: List[String]= List.empty[String]
//TODO add test for salt
  "DES encryption" should {
    "be " in {
      for(password<- passwordList; secret<- secretList)
        des.decrypt( des.encrypt(password, secret), secret).equals(password) shouldBe true
    }

    "allow to re-define the salt value" in{
      aes.algorithm.salt_(salt)
      aes.algorithm.salt.get should be (salt)
    }
  }

  "AES encryption" should{
      "be " in{
        for(password<- passwordList; secret<- secretList)
          aes.decrypt(aes.encrypt(password, secret), secret).equals(password) shouldBe true
      }

      "allow to re-define the salt value" in{
        aes.algorithm.salt_(salt)
        aes.algorithm.salt.get should be (salt)
      }
  }

  "A password encrypted with a Caesar cipher" should{
    "be " in{
      for(password<- passwordList; secret<- secretList)
        caesarCipher.decrypt(caesarCipher.encrypt(password, secret), secret).equals(password) shouldBe true
    }

    "if rotation is 0 then encryption with Caesar cipher should work as identity function" in {
      for (password <- passwordList)
        caesarCipher.decrypt(caesarCipher.encrypt(password, 0), 0).equals(password) shouldBe true
    }

    "not to use salt value" in{
      caesarCipher.algorithm.salt should be (None)
    }
  }

  before {
    val listLength=Random.between(5,20)
    passwordList=listInitializer(listLength)
    secretList=listInitializer(listLength)
    rotation= Random.between(1,50)
    salt =Random.alphanumeric.filter(_.isLetterOrDigit).take(Random.between(8,20)).mkString
  }

  private def listInitializer(listLength:Int )=
    List.fill(listLength)(Random.alphanumeric.filter(_.isLetterOrDigit).take(Random.between(8,20)).mkString)
}
