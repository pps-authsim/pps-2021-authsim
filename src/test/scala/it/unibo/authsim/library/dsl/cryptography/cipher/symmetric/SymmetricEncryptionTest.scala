package it.unibo.authsim.library.dsl.cryptography.cipher.symmetric

import it.unibo.authsim.library.dsl.cryptography.util.DiskManager
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.RSACipher
import it.unibo.authsim.library.user.builder.util.Util.generateRandomString
import scala.util.Random

class SymmetricEncryptionTest extends AnyWordSpec with Matchers with BeforeAndAfter {
  private val des = DESCipher()
  private val aes = AESCipher()
  private val caesarCipher = CaesarCipher()
  private var rotation = 0
  private var salt = ""
  private var passwordList: List[String]= List.empty[String]
  private var secretList: List[String]= List.empty[String]

  private def listInitializer(listLength:Int )=
    List.fill(listLength)(generateRandomString(8,20))

  before {
    val listLength = Random between(5,20)
    passwordList = listInitializer(listLength)
    secretList = listInitializer(listLength)
    rotation = Random between(1,50)
    salt = generateRandomString(8,20)
  }

  "DES cipher" should {
    "use the DES algorithm" in{
      des.algorithm.name shouldBe "DES"
    }
    "be " in {
      for(password<- passwordList; secret<- secretList)
        des decrypt( des encrypt(password, secret), secret) shouldEqual password
    }

    "allow to re-define the salt value" in{
      des.algorithm salt_(salt)
      des.algorithm.salt.get should be (salt)
    }
  }

  "AES encryption" should{
    "use the AES algorithm" in{
      aes.algorithm.name shouldBe "AES"
    }
    "be " in{
      for(password<- passwordList; secret<- secretList)
        aes decrypt(aes encrypt(password, secret), secret) shouldEqual password
    }

    "allow to re-define the salt value" in{
      aes.algorithm salt_(salt)
      aes.algorithm.salt.get should be (salt)
    }
  }

  "A password encrypted with a Caesar cipher" should{
    "use the Caesar cipher algorithm" in{
      caesarCipher.algorithm.name shouldBe "CaesarCipher"
    }
    "be " in{
      for(password<- passwordList; secret<- secretList)
        caesarCipher decrypt(caesarCipher encrypt(password, secret), secret) shouldEqual password
    }

    "if rotation is 0 then encryption with Caesar cipher should work as identity function" in {
      for (password <- passwordList)
        caesarCipher decrypt(caesarCipher encrypt(password, 0), 0) shouldEqual password
    }

    "not to use salt value" in{
      caesarCipher.algorithm.salt should be (None)
    }
  }
}
