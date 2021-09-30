package it.unibo.authsim.library.dsl.otp.util

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import scala.collection.immutable.NumericRange
import scala.collection.mutable.Map as MutableMap
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
      val charsIterator: Iterator[Char] = range.iterator
      var charToSuppress: Char = charsIterator.next
      var indexToSuppress: Option[Int] = base.findIndexChar(charToSuppress)
      while indexToSuppress.isEmpty && charsIterator.hasNext do
        charToSuppress = charsIterator.next
        indexToSuppress = base.findIndexChar(charToSuppress)
      if indexToSuppress.isDefined then base.updated(indexToSuppress.get, charToSuppress) else base


  implicit val generatorSeed: () => Int = () => Random.between(Int.MinValue, Int.MaxValue)

  implicit val generatorLength: OTPPolicy => Int = (policy: OTPPolicy) => Random.between(policy.minimumLength, policy.maximumLength + 1)

  def hmac(hashFunction: HashFunction, secret: String): Array[Byte] =
    val algorithm: String = s"Hmac${hashFunction.getClass.getSimpleName}"
    val mac: Mac = Mac.getInstance(algorithm)
    mac.init(new SecretKeySpec(secret.getBytes, algorithm))
    mac.doFinal(secret.getBytes)

  private case class PreviousGenerateOTP(seed: Int = 0, start: Int = 0, pin: String = ""):
    def isChangeSeed(value: Int): Boolean = this.seed != value
    def regeneratedSamePin(pin: String): Boolean = this.pin == pin

  private var previous: MutableMap[String, PreviousGenerateOTP] = MutableMap.empty // secret -> PreviousGenerateOTP

  def truncate(hashFunction: HashFunction, secret: String, digits: Int, seed: Int)(hmacFunction: (HashFunction, String) => Array[Byte]): String =
    val previousValues: PreviousGenerateOTP = previous.get(secret).getOrElse(PreviousGenerateOTP())
//    println(s"\n\nPrevious = (seed = ${previousValues.seed}, start = ${previousValues.start}, pin = ${previousValues.pin})")
    if previousValues.isChangeSeed(seed) then
//      println(s"length of pin to generate = $digits")
      val hmacStr: String = hmacFunction(hashFunction, secret).map(_.toUInt).mkString
//      println(s"HMAC = $hmacStr, Len = ${hmacStr.length}")
      val start: Int = Random.between(0, hmacStr.length, previousValues.start)
//      println(s"Start: $start")
      var pin = hmacStr.slice(start, start + digits)
      if pin.length < digits then
//        println(s"Add more char : '0'")
        (1 to digits - pin.length).foreach(_ => pin = pin.appended('0'))
      if previousValues.regeneratedSamePin(pin) then
//        println(s"Change one char.")
        pin = pin.change(('0' to '9'))
      previous.update(secret, PreviousGenerateOTP(seed, start, pin))
//      println(s"Now = (seed = $seed, start = $start, pin = $pin)\n\n")
      pin
    else
//      println(s"get previous pin = ${previousValues.pin}")
      previousValues.pin
