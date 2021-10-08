package it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric

import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.key.KeyPair
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.*
import scala.None
import scala.io.Source
import scala.util.Random

class AsymmetricEncryptionTest extends AnyWordSpec with Matchers with BeforeAndAfter {
  val rsa= RSACipher()
  var passwordList: List[String]= List.empty[String]
  val fileName:String= "key.ser"
  val minLength=5
  val maxLength=20
  val keypair= rsa.generateKeys(fileName)
  val(pvt, pub) = (keypair.privateKey, keypair.publicKey)

   "RSA encryption" should {
    "be able to create Key pair"in{
      rsa.generateKeys(fileName) shouldBe a [KeyPair]
    }

    "be able to load keys from a local file or generate a new one if it does not exist" in {
      rsa.loadKeys(fileName) shouldBe a [KeyPair]
    }

    "be able to perform the encryption and the decryption operations" in {
      for (password <- passwordList)
        rsa.decrypt(rsa.encrypt(password, pvt), pub) shouldEqual password
    }
  }

  before {
    val listLength=Random.between(minLength,maxLength)
    passwordList=listInitializer(listLength)
  }

  private def listInitializer(listLength:Int )=
    List.fill(listLength)(Random.alphanumeric.filter(_.isLetterOrDigit).take(Random.between(minLength,maxLength)).mkString)

}
