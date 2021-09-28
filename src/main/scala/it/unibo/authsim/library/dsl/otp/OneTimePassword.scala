package it.unibo.authsim.library.dsl.otp

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.OTPPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import java.util.concurrent.TimeUnit
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import scala.concurrent.duration.Duration
import scala.collection.mutable.Map as MutableMap
import scala.util.Random

import scala.language.postfixOps

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
    private var policy: OTPPolicy = OTPPolicyBuilder() minimumLength 4 maximumLength 10 build
    private var _secret: String = Random.alphanumeric.take(10).mkString

    import OTPHelpers.*

    override def secret(secret: SecretValue) =
      this._secret = s"${secret._1}-${secret._2}"
      this

    override def withPolicy(policy: OTPPolicy) =
      this.policy = policy
      this

    override def generate: String =
      val hotp = truncate(this.hashFunction, this._secret, Random.between(this.policy.minimumLength , this.policy.maximumLength))(hmac)
      pinsGenerated += hotp -> this._secret
      hotp

    override def check(pincode: String): Boolean = (this.pinsGenerated contains pincode) && truncate(this.hashFunction, this.pinsGenerated.get(pincode).get, pincode.length)(hmac) == pincode

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