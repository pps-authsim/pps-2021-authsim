package it.unibo.authsim.library.dsl.otp.builders

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.AbstractTOTPBuilder
import it.unibo.authsim.library.dsl.otp.checkers.OTPChecker
import it.unibo.authsim.library.dsl.otp.generators.OTPGenerator
import it.unibo.authsim.library.dsl.otp.model.{HOTP, TOTP}
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.generatorSeed
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import scala.concurrent.duration.Duration


case class TOTPBuilder() extends AbstractTOTPBuilder:
  override def build: TOTP = new TOTP:

    private var _createDate: Option[Long] = None

    override def length: Int = TOTPBuilder.this._length

    override def createDate: Option[Long] = this._createDate

    override def timeout: Duration = TOTPBuilder.this._timeout

    override def hashFunction: HashFunction = TOTPBuilder.this._hashFunction

    override def policy: OTPPolicy = TOTPBuilder.this._policy

    override def secret: String = TOTPBuilder.this._secret

    override def generate: String =
      val generated = implicitly[(HOTP, Int) => OTPGenerator].apply(this, TOTPBuilder.this._seed).generate
      this._createDate = Some(System.currentTimeMillis())
      generated

    override def check(pincode: String): Boolean =
      val validTime = this.createDate.isDefined && this.createDate.get + this.timeout.toMillis > System.currentTimeMillis()
      if !validTime then this.reset
      validTime && implicitly[(HOTP, Int) => OTPChecker].apply(this, TOTPBuilder.this._seed).check(pincode)

    override def reset: Unit = TOTPBuilder.this.generateSeed

    override def toString: String = s"TOTP = { timeout = $timeout, create date = $createDate, length: $length, hash function = ${hashFunction.getClass.getSimpleName} , secret = $secret, policy = $policy }"