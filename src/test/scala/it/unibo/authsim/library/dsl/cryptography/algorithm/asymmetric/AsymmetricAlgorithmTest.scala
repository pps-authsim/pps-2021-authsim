package it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class AsymmetricAlgorithmTest extends AnyWordSpec with Matchers {
  val rsa = RSA()

  "A RSA algorithm" should {
    "have a name" in {
      rsa.algorithmName shouldBe "RSA"
    }
    "have a default key's length" in {
      rsa.keyLength shouldEqual 2048
    }
    "have a default salt value" in {
      rsa.salt shouldEqual None
    }
    "have allow key length re-definition" in {
      rsa.keyLength_(1024)
      rsa.keyLength shouldEqual 1024
    }
    "do not key length redefinition not complaint with the algorithm rules" in {
      rsa.keyLength_(2)
      rsa.keyLength shouldEqual 1024
    }
  }
}