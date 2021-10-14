package it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric

import it.unibo.authsim.library.user.builder.util.Util._
import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.key.KeyPair
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.*
import scala.util.Random

class AsymmetricEncryptionTest extends AnyWordSpec with Matchers with BeforeAndAfter {
  private val rsa= RSACipher()
  private var passwordList: List[String] = List.empty[String]
  private val fileName:String = "key.ser"
  private val minLength = 5
  private val maxLength = 20
  private val keypair = rsa generateKeys(fileName)
  private val(pvt, pub) = (keypair privateKey, keypair publicKey)

  private def listInitializer(listLength:Int)=
    List.fill(listLength)(generateRandomString(minLength,maxLength))

  before {
    val listLength = Random between(minLength,maxLength)
    passwordList = listInitializer(listLength)
  }

   "RSA cipher" should {
    "use the RSA algorithm" in{
      rsa.algorithm.name shouldBe "RSA"
    }
    "be able to create Key pair"in{
      rsa generateKeys(fileName) shouldBe a [KeyPair]
    }
    "be able to load keys from a local file or generate a new one if it does not exist" in {
      rsa loadKeys(fileName) shouldBe a [KeyPair]
    }
    "be able to perform the encryption and the decryption operations" in {
      for (password <- passwordList)
        rsa decrypt(rsa encrypt(password, pvt), pub) shouldEqual password
    }
  }
}
