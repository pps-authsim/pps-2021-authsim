package it.unibo.authsim.library.dsl.otp.builders

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.builder.Builder
import it.unibo.authsim.library.dsl.otp.checkers.OTPChecker
import it.unibo.authsim.library.dsl.otp.generators.OTPGenerator
import it.unibo.authsim.library.dsl.otp.model.*
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.OTPPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.language.postfixOps
import scala.util.Random

trait OTPBuilder[T] extends Builder[T]:
  def withPolicy(policy: OTPPolicy): this.type
  def secret(secret: OTPBuilder.SecretValue): this.type

object OTPBuilder:
  type SecretValue = (String, String)

  trait HmacOTPBuilder:
    def hashFunction(hashFunction: HashFunction): this.type

  trait TimeOTPBuilder:
    def timeout(timeout: Duration): this.type

  abstract class AbstractOTPBuilder[T] extends OTPBuilder[T]:
    protected val SEPARATOR_SECRET = '-'
    protected var _policy: OTPPolicy = OTPPolicyBuilder() minimumLength 4 maximumLength 10 build
    protected var _secret: String = Random.alphanumeric.take(10).mkString

    override def withPolicy(policy: OTPPolicy) = this.builderMethod[OTPPolicy](policy => this._policy = policy)(policy)

    override def secret(secret: SecretValue) = this.builderMethod[SecretValue](secret => this._secret = s"${secret._1}$SEPARATOR_SECRET${secret._2}")(secret)

  abstract class AbstractHOTPBuilder extends AbstractOTPBuilder[HOTP] with HmacOTPBuilder:
    protected var _hashFunction: HashFunction = HashFunction.SHA256()

    override def hashFunction(hashFunction: HashFunction) = this.builderMethod[HashFunction](hashFunction => this._hashFunction = hashFunction)(hashFunction)

  abstract class AbstractTOTPBuilder extends AbstractOTPBuilder[TOTP] with HmacOTPBuilder with TimeOTPBuilder:
    protected var _timeout: Duration = Duration(1, TimeUnit.MINUTES)
    protected var _hashFunction: HashFunction = HashFunction.SHA256()

    override def hashFunction(hashFunction: HashFunction) = this.builderMethod[HashFunction](hashFunction => this._hashFunction = hashFunction)(hashFunction)

    override def timeout(timeout: Duration) = this.builderMethod[Duration](timeout => this._timeout = timeout)(timeout)