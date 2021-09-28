package it.unibo.authsim.library.dsl.cryptography

import it.unibo.authsim.library.dsl.cryptography.asymmetric.RSA
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.apache.commons.io.FileUtils
import java.io.{File, FileInputStream, FileOutputStream, ObjectOutputStream, PrintWriter}
import scala.io.Source

class AsymmetricEncryptionTest extends AnyWordSpec with Matchers {
  val rsa= RSA()
  val password = "password"
  val keypair= rsa.generateKeys()
  val(pvt, pub)=(keypair.privateKey, keypair.publicKey)
  val passwordEncrypted=rsa.encrypt(password, pub)
  val fileName:String= "key.ser"

   "RSA encryption" should {
    "be able to create Key pair"in{
      rsa.generateKeys().isInstanceOf[Keys] shouldBe true
    }

    "be le to load keys from a local file or generate a new one if it does not exist" in {
      rsa.loadKeys(fileName).isInstanceOf[Keys] shouldBe true
    }

    "be equal to the result of the decryption operation" in {
      rsa.decrypt(rsa.encrypt(password, pub), pvt).equals(password) shouldBe true
    }

  }
}
