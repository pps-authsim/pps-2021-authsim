package it.unibo.authsim.library.dsl.cryptography.asymmetric

import it.unibo.authsim.library.dsl.cryptography.asymmetric.{KeyPair, RSA}
import org.apache.commons.io.FileUtils
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.*
import scala.io.Source

class AsymmetricEncryptionTest extends AnyWordSpec with Matchers {
  val rsa= RSA()
  val password: String = "password"
  val fileName:String= "key.ser"
  val keypair= rsa.generateKeys(fileName)
  val(pvt, pub) = (keypair.privateKey, keypair.publicKey)


   "RSA encryption" should {
    "be able to create Key pair"in{
      rsa.generateKeys(fileName).isInstanceOf[KeyPair] shouldBe true
    }

    "be le to load keys from a local file or generate a new one if it does not exist" in {
      rsa.loadKeys(fileName).isInstanceOf[KeyPair] shouldBe true
    }

    "be equal to the result of the decryption operation" in {
      rsa.decrypt(rsa.encrypt(password, pub), pvt).equals(password) shouldBe true
    }

  }
}
