package it.unibo.authsim.library.dsl.cryptography.util

import it.unibo.authsim.library.dsl.cryptography.util.Base64
import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ImplicitConversionTest extends AnyFlatSpec with Matchers {
  val testString="foo"
  val testString2="999"
  val testArrayByte=Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte])

  def genericToString[A](input: A) = input.isInstanceOf[String]
  def stringToArrayByteTest[A](input: A)= input.isInstanceOf[Array[Byte]]
  def stringToChartArrayTest[A](input: A)= input.isInstanceOf[Array[Char]]
  def genericToIntTest[A](input:A)= input.isInstanceOf[Int]

  "Implicit string to array byte conversion" should "be" in {
    Base64.decodeToArray(testString).isInstanceOf[Array[Byte]] shouldBe true
    Base64.encodeToArray(testString).isInstanceOf[Array[Byte]] shouldBe true
    Base64.encodeToString(testString).isInstanceOf[String] shouldBe true
    Base64.decodeToString(testString).isInstanceOf[String] shouldBe true
    stringToArrayByteTest(testString) shouldBe true
  }

  "Implicit object to int conversion" should "be" in {
    genericToIntTest(testString) shouldBe true
    genericToIntTest(testString2) shouldBe true
    genericToIntTest(testArrayByte) shouldBe true
  }

  "Implicit string to Array char conversion" should "be" in {
    stringToChartArrayTest(testString) shouldBe true
  }

  "Implicit generic to String char conversion" should "be" in {
    //genericToString(1) shouldBe true
    //genericToString(testArrayByte) shouldBe true
  }

}
