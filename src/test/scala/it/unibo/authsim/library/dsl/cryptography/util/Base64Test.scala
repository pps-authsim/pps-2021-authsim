package it.unibo.authsim.library.dsl.cryptography.util

import it.unibo.authsim.library.dsl.cryptography.util.Base64
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class Base64Test extends AnyFlatSpec with Matchers {
  val testArrayByte: Array[Byte]= Array( 0x32.asInstanceOf[Byte], 0x72.asInstanceOf[Byte])
  private val charset: String = "UTF8"
  "Base64 decoding operation" should "be" in {
    Base64.decodeToArray(testArrayByte).isInstanceOf[Array[Byte]] shouldBe true
    Base64.decodeToString(testArrayByte).isInstanceOf[String] shouldBe true
  }

  "Base64 encoding operation" should "be" in {
    Base64.encodeToArray(testArrayByte).isInstanceOf[Array[Byte]] shouldBe true
    Base64.encodeToString(testArrayByte).isInstanceOf[String] shouldBe true
  }
}
