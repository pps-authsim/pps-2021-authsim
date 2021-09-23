package it.unibo.authsim.library.dsl.cryptography

import it.unibo.authsim.library.dsl.cryptography.asymmetric.{RSA}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class AsymmetricEncryptionTest extends AnyWordSpec with Matchers {
  val rsa= RSA()
  val password = "password"
  val keypair= rsa.generateKeys()
  val(pvt, pub)=(keypair.privateKey, keypair.publicKey)
  val passwordEncrypted=rsa.encrypt(password, pub)

  "RSA encryption" should {
    "be equal to the result of the decryption operation" in {
      rsa.decrypt(passwordEncrypted, pvt).equals(password) shouldBe true
    }
  }
}