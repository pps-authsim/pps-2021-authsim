package it.unibo.authsim.library.dsl.cryptography.util

import it.unibo.authsim.library.dsl.cryptography.util.Base64
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._
import it.unibo.authsim.library.dsl.cryptography.utility.ImplicitConversionChecker._
class ImplicitConversionTest extends AnyFlatSpec with Matchers {

  val testString="foo"
  val testString2="999"

  "Implicit string to array byte conversion" should "be" in {
    canConvert[String, Array[Byte]]() shouldBe true
    canConvert[Int,  Array[Byte]]() shouldBe false
  }

  "Implicit object to int conversion" should "be" in {
      canConvert[String, Int]() shouldBe true
      canConvert[Array[Byte], Int]() shouldBe true
      canConvert[Boolean, Int]() shouldBe true
  }

  "and default conversion to int" should " be "  in{
    optConvert[String, Int](this.testString2) shouldBe Option(999)
  }
  "and conversion of a reasonable value to int" should " be "  in{
    optConvert[String, Int](this.testString) shouldBe Option(0)
  }

  "Implicit string to Array char conversion" should "be" in {
    canConvert[String, Array[Char]]() shouldBe true
    canConvert[Int, Array[Char]]() shouldBe false
    canConvert[String, Array[Int]]() shouldBe false
  }

  "Implicit generic to String char conversion" should "be" in {
    canConvert[String, String]() shouldBe true
    canConvert[Array[Byte], String]() shouldBe true
    canConvert[Boolean, String]() shouldBe true
  }
}
