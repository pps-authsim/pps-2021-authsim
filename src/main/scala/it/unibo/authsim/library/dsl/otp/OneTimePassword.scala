package it.unibo.authsim.library.dsl.otp

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import java.util.concurrent.TimeUnit
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import scala.concurrent.duration.Duration
import scala.collection.mutable.Map as MutableMap
import scala.util.Random

trait OneTimePassword:
  def withPolicy(policy: OTPPolicy): this.type
  def generate: String
  def check(pincode: String): Boolean

object OneTimePassword:

  trait HOTP extends OneTimePassword:
    type SecretValue = (String, String)
    def hashFunction: HashFunction
    def secret(secret: SecretValue): this.type

  trait TOTP extends OneTimePassword with HOTP:
    def timeout: Duration

  def apply(hashFunction: HashFunction): HOTP = new HOTPImpl(hashFunction)

  def apply(timeout: Duration, hashFunction: HashFunction = HashFunction.SHA256()): TOTP = new TOTPImpl(timeout, hashFunction)


  private class HOTPImpl(override val hashFunction: HashFunction) extends HOTP:
    private val pinsGenerated: MutableMap[String, String] = MutableMap.empty

    private val minLenDefault: Int = 4
    private val maxLenDefault: Int = 10

    private var policy: Option[OTPPolicy] = None
    private var _secret: String = Random.alphanumeric.take(10).mkString

    override def secret(secret: SecretValue) =
      this._secret = s"${secret._1}-${secret._2}"
      this

    override def withPolicy(policy: OTPPolicy) =
      this.policy = Some(policy)
      this

    override def generate: String =
      val min: Int = if this.policy.isDefined then this.policy.get.minimumLength else this.minLenDefault;
      val max: Int = if this.policy.isDefined then this.policy.get.maximumLength else this.maxLenDefault;
      val hotp = OTPHelpers.truncate(this.hashFunction, this._secret, Random.between(min, max))(OTPHelpers.hmac)
      pinsGenerated += hotp -> this._secret
      hotp

    override def check(pincode: String): Boolean = (this.pinsGenerated contains pincode) && OTPHelpers.truncate(this.hashFunction, this.pinsGenerated.get(pincode).get, pincode.length)(OTPHelpers.hmac) == pincode

  private class TOTPImpl(override val timeout: Duration, override val hashFunction: HashFunction) extends HOTPImpl(HashFunction.SHA256()) with TOTP:

    def this() = this(Duration(1, TimeUnit.MINUTES), HashFunction.SHA256())

    private val pinsGenerated: MutableMap[String, Long] = MutableMap.empty

    override def secret(secret: SecretValue) = super.secret(secret)

    override def withPolicy(policy: OTPPolicy) = super.withPolicy(policy)

    override def generate: String =
      val generated: String = super.generate
      this.pinsGenerated += generated -> System.currentTimeMillis()
      generated

    override def check(pincode: String): Boolean = (this.pinsGenerated contains pincode) && super.check(pincode) && this.pinsGenerated.get(pincode).get + this.timeout.toMillis > System.currentTimeMillis()

  private object OTPHelpers:

    def hmac(hashFunction: HashFunction, secret: String): Array[Byte] =
      val algorithm: String = s"Hmac${hashFunction.toString.replace("()", "")}"
      val mac: Mac = Mac.getInstance(algorithm)
      mac.init(new SecretKeySpec(secret.getBytes, algorithm))
      mac.doFinal()

    def truncate(hashFunction: HashFunction, secret: String, digits: Int)(hmacFunction: (HashFunction, String) => Array[Byte]): String =
      val hmacStr: String = hmacFunction(hashFunction, secret).map(_.toInt & 0xff).mkString
      hmacStr.slice(hmacStr.length - digits, hmacStr.length)