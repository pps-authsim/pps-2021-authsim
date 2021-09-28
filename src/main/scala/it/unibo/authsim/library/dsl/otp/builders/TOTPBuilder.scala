package it.unibo.authsim.library.dsl.otp.builders

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.AbstractTOTPBuilder
import it.unibo.authsim.library.dsl.otp.checkers.OTPChecker
import it.unibo.authsim.library.dsl.otp.generators.OTPGenerator
import it.unibo.authsim.library.dsl.otp.model.{HOTP, TOTP}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import scala.concurrent.duration.Duration


case class TOTPBuilder() extends AbstractTOTPBuilder:
  override def build: TOTP = new TOTP:

    private var _createDate: Option[Long] = None

    override def createDate: Option[Long] = this._createDate

    override def timeout: Duration = TOTPBuilder.this._timeout

    override def hashFunction: HashFunction = TOTPBuilder.this._hashFunction

    override def policy: OTPPolicy = TOTPBuilder.this._policy

    override def secret: String = TOTPBuilder.this._secret

    override def generate: String =
      val generated = implicitly[HOTP => OTPGenerator].apply(this).generate
      this._createDate = Some(System.currentTimeMillis())
      generated

    override def check(pincode: String): Boolean =
      implicitly[HOTP => OTPChecker].apply(this).check(pincode) && this.createDate.isDefined && this.createDate.get + this.timeout.toMillis > System.currentTimeMillis()

    override def toString: String = s"TOTP = { timeout = $timeout, create date = $createDate, hash function = ${hashFunction.getClass.getSimpleName} , secret = $secret, policy = $policy }"