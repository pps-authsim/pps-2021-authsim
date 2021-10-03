package it.unibo.authsim.library.dsl.cryptography.util

import it.unibo.authsim.library.dsl.cryptography.util.Base64
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._
import it.unibo.authsim.library.dsl.cryptography.utility.ImplicitConversionChecker._
class ImplicitConversionTest extends AnyFlatSpec with Matchers {

  private val testString="foo"
  private val testString2="999"

  "Implicit generic to array byte conversion" should "be" in {
    isConversionAvailable[String, Array[Byte]]() shouldBe true
    isConversionAvailable[Boolean,  Array[Byte]]() shouldBe true
    isConversionAvailable[Int,  Array[Byte]]() shouldBe true
  }

  "Implicit object to int conversion" should "be" in {
      isConversionAvailable[String, Int]() shouldBe true
      isConversionAvailable[Array[Byte], Int]() shouldBe true
      isConversionAvailable[Boolean, Int]() shouldBe true
  }

  "and default conversion to int" should " be "  in{
    convert[String, Int](this.testString2) shouldBe Option(999)
  }
  "and conversion of a reasonable value to int" should " be "  in{
    convert[String, Int](this.testString) shouldBe Option(0)
  }

  "Implicit string to Array char conversion" should "be" in {
    isConversionAvailable[String, Array[Char]]() shouldBe true
    isConversionAvailable[Int, Array[Char]]() shouldBe false
    isConversionAvailable[String, Array[Int]]() shouldBe false
  }

  "Implicit generic to String char conversion" should "be" in {
    isConversionAvailable[String, String]() shouldBe true
    isConversionAvailable[Array[Byte], String]() shouldBe true
    isConversionAvailable[Boolean, String]() shouldBe true
  }
}
