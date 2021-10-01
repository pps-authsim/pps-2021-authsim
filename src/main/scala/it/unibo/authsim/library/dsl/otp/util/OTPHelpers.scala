package it.unibo.authsim.library.dsl.otp.util

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import scala.collection.immutable.NumericRange
import scala.language.postfixOps
import scala.util.Random

object OTPHelpers:

  implicit class RichInt(base: Int):
    def ++ : Int = base + 1

  implicit class RichByte(base: Byte):
    def toUInt: Int = base & 0xff

  implicit class RichRandom(base: Random):
    def between(minInclusive: Int, maxExclusive: Int, excludeVal: Int): Int =
      var value: Int = excludeVal
      while excludeVal == value do value = base.between(minInclusive, maxExclusive)
      value

  implicit class RichString(base: String):
    def findIndexChar(differentFrom: Char): Option[Int] = base.zipWithIndex.find((char, _) => char != differentFrom).map((_, index) => index)

    def change(range: NumericRange[Char]): String =
      require(!range.isEmpty, "range must have at least one value")
      val charsIterator: Iterator[Char] = range.iterator
      var charToSuppress: Char = charsIterator.next
      var indexToSuppress: Option[Int] = base.findIndexChar(charToSuppress)
      while indexToSuppress.isEmpty && charsIterator.hasNext do
        charToSuppress = charsIterator.next
        indexToSuppress = base.findIndexChar(charToSuppress)
      if indexToSuppress.isDefined then base.updated(indexToSuppress.get, charToSuppress) else base


  implicit val generatorSeed: () => Int = () => Random.between(Int.MinValue, Int.MaxValue)

  implicit val generatorLength: OTPPolicy => Int = (policy: OTPPolicy) => Random.between(policy.minimumLength, policy.maximumLength + 1)