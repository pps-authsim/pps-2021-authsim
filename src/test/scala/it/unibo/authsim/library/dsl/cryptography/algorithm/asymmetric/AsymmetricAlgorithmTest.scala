package it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class AsymmetricAlgorithmTest extends AnyWordSpec with Matchers {
  private val rsa = RSA()
  private val defaultKey=2048
  private val newKey=1024
  private val wrongKey=2

  s"A '${rsa name}' algorithm" should {
    "have a name" in {
      rsa.name shouldBe "RSA"
    }
    "have a default key's length" in {
      rsa.keyLength shouldEqual defaultKey
    }
    "have a default salt value" in {
      rsa.salt shouldEqual None
    }
    "allow key length re-definition" in {
      rsa.keyLength_(newKey)
      rsa.keyLength shouldEqual newKey
    }
    "do not allow a key re-definition not complaint with the algorithm rules" in {
      rsa.keyLength_(wrongKey)
      rsa.keyLength shouldEqual newKey
    }
  }
}