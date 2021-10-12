package it.unibo.authsim.library.dsl.otp.builders

import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.builder.Builder
import it.unibo.authsim.library.dsl.otp.generator.OTPGenerator
import it.unibo.authsim.library.dsl.otp.model.*
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.*
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.OTPPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.language.postfixOps
import scala.util.Random

/**
 * ''OTPBuilder'' is a trait that is used to build an new one time password of the type T
 * @tparam T the type to build
 */
trait OTPBuilder[T] extends Builder[T]:
  /**
   * Set a [[OTPPolicy one time password policy]]
   * @param policy otp policy to set
   * @param generateLength a generator of the actual length of one time password given a otp policy
   * @return instance of the actual builder
   */
  def withPolicy(policy: OTPPolicy)(implicit generateLength: OTPPolicy => Int): this.type

object OTPBuilder:
  /**
   * It rappresents the user's userID and password pair.
   */
  type SecretValue = (String, String)

  /**
   *  ''SecretBuilder'' rappresents an extension builder to use Secret value.
   */
  trait SecretBuilder:
    /**
     * Set the secret key (@see [[SecretValue]])
     * @param secret key to set
     * @return instance of the actual builder
     */
    def secret(secret: SecretValue): this.type

  /**
   *  ''HmacOTPBuilder'' rappresents an extension builder to implement HOTP.
   */
  trait HmacOTPBuilder:
    /**
     * Set the [[HashFunction hash function]] used to generate the otp.
     * @param hashFunction hash function to set
     * @return instance of the actual builder
     */
    def hashFunction(hashFunction: HashFunction): this.type

  /**
   * ''TimeOTPBuilder'' rappresents an extension builder to implement TOTP.
   */
  trait TimeOTPBuilder:
    /**
     * Set the [[Duration time]] that rappresents the expiration of one time password
     * @param timeout time to set
     * @return instance of the actual builder
     */
    def timeout(timeout: Duration): this.type

  /**
   * ''AbstractOTPBuilder'' is an abstract builder that implements [[OTPBuilder]] methods.
   *
   * Default:
   *  - [[OTPPolicy OTP policy]] have a minimum length of 4 numbers and a maximum of 10 numbers.
   *  - [[SecretValue Secret value]] was a random string of 10 characters.
   *
   * @tparam T  the type to build
   */
  abstract class AbstractOTPBuilder[T] extends OTPBuilder[T] with SecretBuilder:
    protected val SEPARATOR_SECRET = '-'
    protected var _policy: OTPPolicy = null
    protected var _secret: String = Random.alphanumeric.take(10).mkString

    protected var _length: Int = 0
    this.withPolicy(OTPPolicyBuilder() minimumLength 4 maximumLength 10 build)

    protected var _seed: Int = 0
    this.generateSeed

    protected def generateSeed(implicit seedGenerator: () => Int): Unit =
      val genSeed: Int = seedGenerator()
      this._seed = if genSeed == this._seed then genSeed + 1 else genSeed

    override def withPolicy(policy: OTPPolicy)(implicit lengthGenerator: OTPPolicy => Int) =
      this.builderMethod[OTPPolicy](policy =>
        this._policy = policy;
        this._length = lengthGenerator(policy)
      )(policy)

    override def secret(secret: SecretValue) = this.builderMethod[SecretValue](secret => this._secret = s"${secret._1}$SEPARATOR_SECRET${secret._2}")(secret)

  /**
   * ''AbstractHOTPBuilder'' is an abstract builder that extends [[AbstractOTPBuilder]] and implements [[HmacOTPBuilder]] methods
   *
   *  Default:
   *  - [[HashFunction Hash function]] is [[HashFunction.SHA256 SHA256]].
   */
  abstract class AbstractHOTPBuilder extends AbstractOTPBuilder[HOTP] with HmacOTPBuilder:
    protected var _hashFunction: HashFunction = HashFunction.SHA256()

    protected def otpGenerator(length: Int = this._length): String = OTPGenerator(this._hashFunction, this._secret, length, this._seed)

    override def hashFunction(hashFunction: HashFunction) = this.builderMethod[HashFunction](hashFunction => this._hashFunction = hashFunction)(hashFunction)

  /**
   * ''AbstractTOTPBuilder'' is an abstract builder that extends [[AbstractHOTPBuilder]] and implements [[TimeOTPBuilder]] methods
   *
   * Default:
   *  - [[Duration Time of expiration]] has a duration of 1 minute.
   */
  abstract class AbstractTOTPBuilder extends AbstractHOTPBuilder with TimeOTPBuilder:
    protected var _timeout: Duration = Duration(1, TimeUnit.MINUTES)

    override def timeout(timeout: Duration) = this.builderMethod[Duration](timeout => this._timeout = timeout)(timeout)