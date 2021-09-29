package it.unibo.authsim.library.dsl.otp.builders

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.builder.Builder
import it.unibo.authsim.library.dsl.otp.checkers.OTPChecker
import it.unibo.authsim.library.dsl.otp.generators.OTPGenerator
import it.unibo.authsim.library.dsl.otp.model.*
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.*
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.OTPPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.language.postfixOps
import scala.util.Random

trait OTPBuilder[T] extends Builder[T]:
  def withPolicy(policy: OTPPolicy)(implicit generateLength: OTPPolicy => Int): this.type
  def secret(secret: OTPBuilder.SecretValue): this.type

object OTPBuilder:
  type SecretValue = (String, String)

  trait HmacOTPBuilder:
    def hashFunction(hashFunction: HashFunction): this.type

  trait TimeOTPBuilder:
    def timeout(timeout: Duration): this.type

  abstract class AbstractOTPBuilder[T] extends OTPBuilder[T]:
    protected val SEPARATOR_SECRET = '-'
    protected var _policy: OTPPolicy = null
    protected var _secret: String = Random.alphanumeric.take(10).mkString

    protected var _length: Int = 0
    this.withPolicy(OTPPolicyBuilder() minimumLength 4 maximumLength 10 build)

    protected var _seed: Int = 0
    this.generateSeed

    protected def generateSeed(implicit seedGenerator: () => Int): Unit =
      val genSeed: Int = seedGenerator()
      this._seed = if genSeed == this._seed then genSeed++ else genSeed

    override def withPolicy(policy: OTPPolicy)(implicit lengthGenerator: OTPPolicy => Int) =
      this.builderMethod[OTPPolicy](policy =>
        this._policy = policy;
        this._length = lengthGenerator(policy)
      )(policy)

    override def secret(secret: SecretValue) = this.builderMethod[SecretValue](secret => this._secret = s"${secret._1}$SEPARATOR_SECRET${secret._2}")(secret)

  abstract class AbstractHOTPBuilder extends AbstractOTPBuilder[HOTP] with HmacOTPBuilder:
    protected var _hashFunction: HashFunction = HashFunction.SHA256()

    override def hashFunction(hashFunction: HashFunction) = this.builderMethod[HashFunction](hashFunction => this._hashFunction = hashFunction)(hashFunction)

  abstract class AbstractTOTPBuilder extends AbstractHOTPBuilder with TimeOTPBuilder:
    protected var _timeout: Duration = Duration(1, TimeUnit.MINUTES)

    override def timeout(timeout: Duration) = this.builderMethod[Duration](timeout => this._timeout = timeout)(timeout)