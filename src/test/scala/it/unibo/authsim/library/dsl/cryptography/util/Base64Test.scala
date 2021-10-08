package it.unibo.authsim.library.dsl.cryptography.util

import it.unibo.authsim.library.dsl.cryptography.util.Base64
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class Base64Test extends AnyFlatSpec with Matchers {
  val testArrayByte: Array[Byte]= Array( 0x32.asInstanceOf[Byte], 0x72.asInstanceOf[Byte])
  private val charset: String = "UTF8"
  "Base64 decoding operation from array byte to array byte" should "be" in {
    Base64.decodeToArray(testArrayByte) shouldBe a [Array[Byte]]
  }
  "Base64 decoding operation from array byte to string" should "be" in {
    Base64.decodeToString(testArrayByte) shouldBe a [String]
  }

  "Base64 encoding operation from array byte to array byte" should "be" in {
    Base64.encodeToArray(testArrayByte) shouldBe a [Array[Byte]]
  }
  "Base64 encoding operation from array byte to String" should "be" in {
    Base64.encodeToString(testArrayByte) shouldBe a [String]
  }
}
