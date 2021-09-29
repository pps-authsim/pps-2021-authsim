package it.unibo.authsim.library.dsl.otp.util

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import scala.util.Random

import scala.language.postfixOps

object OTPHelpers:

  implicit class RichInt(base: Int):
    def ++ : Int = base + 1

  private implicit class RichByte(base: Byte):
    def toUInt: Int = base & 0xff

  implicit val generatorSeed: () => Int = () => Random.between(0, 10)
  implicit val generatorLength: OTPPolicy => Int = (policy: OTPPolicy) => Random.between(policy.minimumLength, policy.maximumLength + 1)

  def hmac(hashFunction: HashFunction, secret: String): Array[Byte] =
    val algorithm: String = s"Hmac${hashFunction.getClass.getSimpleName}"
    val mac: Mac = Mac.getInstance(algorithm)
    mac.init(new SecretKeySpec(secret.getBytes, algorithm))
    mac.doFinal()

  def truncate(hashFunction: HashFunction, secret: String, digits: Int, seed: Int)(hmacFunction: (HashFunction, String) => Array[Byte]): String =
    val hmacStr: String = hmacFunction(hashFunction, secret).map(_.toUInt).mkString
    val start: Int = hmacStr.length - digits - seed
    val end: Int = hmacStr.length - seed
    //TODO: CHECKS IF SEED IS GREATER THAN LEN HMAC STRING
    //println(s"Len = $digits, seed = $seed, (start) = $start, (end) = $end, HMAC stri len = ${hmacStr.length}")
    hmacStr.slice(start, end)
