package it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.*
import org.scalatest.BeforeAndAfter

import scala.util.Random

class SymmetricAlgorithmTest extends AnyWordSpec with Matchers with BeforeAndAfter {
  private val des= DES()
  private val aes= AES()
  private val caesar= Caesar()
  private val desKey= 7
  private val aesKey= (16, 32)
  private val caesarKey= 8
  private val wrongKey=2
  private var salt=""
  private val minLength=5
  private val maxLength=20

  s"A '${des.algorithmName}' algorithm" should {
    "have a name" in{
      des.algorithmName shouldBe "DES"
    }
    "have a default key's length" in{
      des.keyLength shouldEqual desKey
    }
    "not to have a default salt value" in{
      des.salt shouldEqual None
    }
    "allow to set the salt value" in{
      des.salt_(salt)
      des.salt.get shouldEqual salt
    }
  }

  s"A '${aes.algorithmName}' algorithm" should {
    "have a name" in{
      aes.algorithmName shouldBe "AES"
    }
    "have a default key's length" in{
      aes.keyLength shouldEqual aesKey._1
    }
    "have a default salt value" in{
      aes.salt shouldEqual None
    }
    "allow to set the salt" in{
      aes.salt_(salt)
      aes.salt.get shouldEqual salt
    }
    "have allow key length re-definition"in{
      aes.keyLength_(aesKey._2)
      aes.keyLength shouldEqual aesKey._2
    }
    "do not key length redefinition not complaint with the algorithm rules" in{
      aes.keyLength_(wrongKey)
      aes.keyLength shouldEqual aesKey._2
    }
  }

  s"A '${caesar.algorithmName}' algorithm" should {
    "have a name" in{
      caesar.algorithmName shouldBe "CaesarCipher"
    }
    "have a default key's length" in{
      caesar.keyLength shouldEqual caesarKey
    }
    "not have a salt value" in{
      caesar.salt shouldEqual None
    }
  }
  before {
    salt= Random.alphanumeric.filter(_.isLetterOrDigit).take(Random.between(minLength,maxLength)).mkString
  }
}
