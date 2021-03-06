package it.unibo.authsim.library.otp.util

import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.policy.model.StringPolicies.OTPPolicy

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
      if charsIterator.hasNext then
        val charReplaced: Char = charsIterator.next
        val indexToReplace: Option[Int] = base.findIndexChar(charReplaced)
        indexToReplace match
          case Some(index) => base.updated(index, charReplaced)
          case None => replaceFirstDifferent(range.drop(1))
      else base