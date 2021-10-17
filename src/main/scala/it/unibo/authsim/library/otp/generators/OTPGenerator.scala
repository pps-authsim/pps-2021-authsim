package it.unibo.authsim.library.otp.generators

import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.otp.util.OTPHelpers.*

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import scala.collection.mutable.Map as MutableMap
import scala.util.Random

/**
 * ''OTPGenerator'' is object that rappresent the actual implementation of the HMAC One-Time Password algorithm.
 */
object OTPGenerator:

  private case class PreviousGenerateOTP(seed: Option[Int] = None, start: Option[Int] = Some(0), pin: Option[String] = None):
    def isChangeSeed(value: Int): Boolean = this.seed.isEmpty || this.seed.get != value
    def regeneratedSamePin(pin: String): Boolean = this.pin.isDefined && this.pin.get == pin

  private val previous: MutableMap[String, PreviousGenerateOTP] = MutableMap.empty // secret -> PreviousGenerateOTP

  private def hmac(hashFunction: HashFunction, secret: String): Array[Byte] =
    val algorithm: String = s"Hmac${hashFunction.getClass.getSimpleName}"
    val mac: Mac = Mac.getInstance(algorithm)
    mac.init(new SecretKeySpec(secret.getBytes, algorithm))
    mac.doFinal(secret.getBytes)

  /**
   * @param hashFunction cryptographic hash method
   * @param secret secret key which is an arbitrary string
   * @param digits actual length of hotp
   * @param seed value that rappresents the session of the generated otp
   * @return a generated one time password string based on HMAC algorithm
   * @throws NoSuchAlgorithmException whether the hash function algorithm is not supported.
   */
  def apply(hashFunction: HashFunction, secret: String, digits: Int, seed: Int): String =
    val previousValues: PreviousGenerateOTP = previous.get(secret).getOrElse(PreviousGenerateOTP())
    if previousValues.isChangeSeed(seed) then
      val hmacStr: String = this.hmac(hashFunction, secret).map(_.toUInt).mkString
      Random.setSeed(seed)
      val start: Int = Random.between(0, hmacStr.length, previousValues.start.get)
      var pin = hmacStr.slice(start, start + digits)
      if pin.length < digits then (1 to digits - pin.length).foreach(_ => pin = pin.appended('0'))
      if previousValues.regeneratedSamePin(pin) then pin = pin.replaceFirstDifferent(('0' to '9'))
      previous.update(secret, PreviousGenerateOTP(Some(seed), Some(start), Some(pin)))
      pin
    else
      previousValues.pin.get
