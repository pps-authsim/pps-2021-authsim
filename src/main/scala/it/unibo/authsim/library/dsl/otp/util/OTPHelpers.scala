package it.unibo.authsim.library.dsl.otp.util

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import scala.collection.immutable.NumericRange
import scala.language.postfixOps
import scala.util.Random

/**
 * ''OTPHelpers'' is object that has the usefull functions for otp implementation.
 */
object OTPHelpers:

  implicit class RichByte(base: Byte):
    /**
     * Convert a byte to unsigned byte in an integer.
     * @return the unsigned integer of byte ''base''
     */
    def toUInt: Int = base & 0xff

  implicit class RichRandom(base: Random):
    /**
     * @param minInclusive minimum integer (included) of the random sequence
     * @param maxExclusive maximum integer (excluded) of the random sequence
     * @param excludeVal the integer to exclude from range [minInclusive, maxExclusive[
     * @return the next pseudorandom between min (inclusive) and max (exclusive) from random number generator's sequence excluding the value ''excludeVal'''
     */
    def between(minInclusive: Int, maxExclusive: Int, excludeVal: Int): Int =
      var value: Int = excludeVal
      while excludeVal == value do value = base.between(minInclusive, maxExclusive)
      value

  implicit class RichString(base: String):
    /**
     * @param differentFrom character to ignore
     * @return an optional integer that rappresent the index of the first character different from ''differentFrom''
     */
    def findIndexChar(differentFrom: Char): Option[Int] = base.zipWithIndex.find((char, _) => char != differentFrom).map((_, index) => index)
    /**
     * @param range range of characters
     * @return the string replaced by a character included in the range (but different from the replaced),
     *         otherwise original string
     * @throws IllegalArgumentException whether the ''range'' is empty
     */
    def replaceFirstDifferent(range: NumericRange[Char]): String =
      require(!range.isEmpty, "range must have at least one value")
      val charsIterator: Iterator[Char] = range.iterator
      var charReplaced: Char = charsIterator.next
      var indexToSuppress: Option[Int] = base.findIndexChar(charReplaced)
      while indexToSuppress.isEmpty && charsIterator.hasNext do
        charReplaced = charsIterator.next
        indexToSuppress = base.findIndexChar(charReplaced)
      if indexToSuppress.isDefined then base.updated(indexToSuppress.get, charReplaced) else base

  /**
   * A default implementation of an seed generator (@see [[it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.AbstractOTPBuilder.generateSeed]])
   */
  implicit val generatorSeed: () => Int = () => Random.between(Int.MinValue, Int.MaxValue)

  /**
   * A default implementation of an length generator (@see [[it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.withPolicy]])
   */
  implicit val generatorLength: OTPPolicy => Int = (policy: OTPPolicy) => Random.between(policy.minimumLength, policy.maximumLength.getOrElse(policy.minimumLength + 10) + 1)