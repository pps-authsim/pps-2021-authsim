package it.unibo.authsim.library.dsl.otp.util

import it.unibo.authsim.library.dsl.HashFunction

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object OTPHelpers:

  private implicit class RichInt(base: Byte):
    def toUInt: Int = base & 0xff

  def hmac(hashFunction: HashFunction, secret: String): Array[Byte] =
    val algorithm: String = s"Hmac${hashFunction.getClass.getSimpleName}"
    val mac: Mac = Mac.getInstance(algorithm)
    mac.init(new SecretKeySpec(secret.getBytes, algorithm))
    mac.doFinal()

  def truncate(hashFunction: HashFunction, secret: String, digits: Int)(hmacFunction: (HashFunction, String) => Array[Byte]): String =
    val hmacStr: String = hmacFunction(hashFunction, secret).map(_.toUInt).mkString
    hmacStr.slice(hmacStr.length - digits, hmacStr.length)
