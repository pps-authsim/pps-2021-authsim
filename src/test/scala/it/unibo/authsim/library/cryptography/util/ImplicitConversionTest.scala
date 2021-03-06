package it.unibo.authsim.library.cryptography.util

import it.unibo.authsim.library.cryptography.util.Base64
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import it.unibo.authsim.library.cryptography.util.ImplicitConversion.ImplicitToArray._
import it.unibo.authsim.library.cryptography.util.ImplicitConversion.ImplicitConversionToBuiltinType._
import it.unibo.authsim.library.cryptography.util.ImplicitConversion.ImplicitToList._
import it.unibo.authsim.library.cryptography.util.ImplicitConversion.ImplicitToSeq.listToSeq
import it.unibo.authsim.library.cryptography.utility.ImplicitConversionChecker.*
import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec
class ImplicitConversionTest extends AnyFeatureSpec with GivenWhenThen with Matchers {
  private val testString="foo"
  private val testString2="999"

  feature ("Implicit conversion from generic to Array[Byte]"){
    scenario ("is conversion available from String") {
      isConversionAvailable[String, Array[Byte]]() shouldBe true
    }
    scenario ("is conversion available from Boolean") {
      isConversionAvailable[Boolean, Array[Byte]]() shouldBe true
    }
    scenario ("is conversion available from Int") {
      isConversionAvailable[Int, Array[Byte]]() shouldBe true
    }
  }

  feature ("Implicit conversion from generic to int"){
    scenario ("is conversion available from String") {
      isConversionAvailable[String, Int]() shouldBe true
    }
    scenario ("is conversion available from Array[Byte]") {
      isConversionAvailable[Array[Byte], Int]() shouldBe true
    }
    scenario ("is conversion available from Boolean") {
      isConversionAvailable[Boolean, Int]() shouldBe true
    }
    scenario("and default conversion to int" ) {
      convert[String, Int](this.testString2) shouldBe Option(999)
    }
    scenario("and conversion of a reasonable value to int"){
      convert[String, Int](this.testString) shouldBe Option(0)
    }
  }

  feature("Implicit conversion from string to Array char") {
    scenario ("is conversion available from String") {
      isConversionAvailable[String, Array[Char]]() shouldBe true
    }
    scenario ("is conversion available from Int") {
      isConversionAvailable[Int, Array[Char]]() shouldBe false
    }
    scenario ("is conversion available from String to Array[Int]") {
      isConversionAvailable[String, Array[Int]]() shouldBe false
    }
  }

  feature("Implicit conversion from generic to String") {
    scenario("is conversion available from Set[Int]") {
      isConversionAvailable[Set[Int], String]() shouldBe true
    }
    scenario("is conversion available from Array[Byte]") {
      isConversionAvailable[Array[Byte], String]() shouldBe true
    }
    scenario("is conversion available from Boolean") {
      isConversionAvailable[Boolean, String]() shouldBe true
    }
  }

  feature("Implicit conversion from String") {
    scenario("is conversion available to Byte") {
      isConversionAvailable[String, Byte]() shouldBe true
    }
  }

  feature("Implicit conversion from generic array to sequence"){
    import it.unibo.authsim.library.cryptography.util.ImplicitConversion.ImplicitToSeq.arrayToSeq
    scenario("is conversion available to int sequence") {
      isConversionAvailable[Array[Int], Seq[Int]]() shouldBe true
    }
    scenario("is conversion available to string sequence") {
      isConversionAvailable[Array[String], Seq[String]]() shouldBe true
    }
  }

  feature("Implicit conversion from a generic list to sequence"){
    scenario("is conversion available to int sequence") {
      isConversionAvailable[List[Int], Seq[Int]]() shouldBe true
    }
    scenario("is conversion available to string sequence") {
      isConversionAvailable[List[String], Seq[String]]() shouldBe true
    }
  }

  feature("Implicit conversion from a generic array to list"){
    scenario("is conversion available to int list") {
      isConversionAvailable[Seq[Int], List[Int]]() shouldBe true
    }
    scenario("is conversion available to string list") {
      isConversionAvailable[Seq[String], List[String]]() shouldBe true
    }
  }

  feature("Implicit conversion from a generic sequence to array"){
    scenario("is conversion available to int array") {
      isConversionAvailable[Seq[Int], Array[Int]]() shouldBe true
    }
  }

  feature("Implicit conversion from a generic list to array"){
    scenario("is conversion available to int array") {
      isConversionAvailable[List[Int], Array[Int]]() shouldBe true
    }
    scenario("is conversion available to string list") {
      isConversionAvailable[List[String], Array[String]]() shouldBe true
    }
  }
}

