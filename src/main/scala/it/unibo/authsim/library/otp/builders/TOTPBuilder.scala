package it.unibo.authsim.library.otp.builders

import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.otp.builders.OTPBuilder.AbstractTOTPBuilder
import it.unibo.authsim.library.otp.model.TOTP
import it.unibo.authsim.library.policy.model.StringPolicies.OTPPolicy

import scala.concurrent.duration.Duration


/**
 * ''TOTPBuilder'' is the Time-based One-Time Password (TOTP) builder.
 */
case class TOTPBuilder() extends AbstractTOTPBuilder:
  override def build: TOTP = new TOTP:

    private var _createDate: Option[Long] = None

    override def length: Int = TOTPBuilder.this._length

    override def createDate: Option[Long] = this._createDate

    override def timeout: Duration = TOTPBuilder.this._timeout

    override def hashFunction: HashFunction = TOTPBuilder.this._hashFunction

    override def policy: OTPPolicy = TOTPBuilder.this._policy

    override def generate: String =
      this._createDate = Some(System.currentTimeMillis())
      TOTPBuilder.this.otpGenerator()

    override def check(pincode: String): Boolean =
      val validTime = this.createDate.isDefined && this.createDate.get + this.timeout.toMillis > System.currentTimeMillis()
      if !validTime then this.reset
      TOTPBuilder.this.checked(validTime && TOTPBuilder.this.otpGenerator(pincode.length) == pincode)

    override def reset: Unit = TOTPBuilder.this.reset

    override def toString: String = s"TOTP = { timeout = $timeout, create date = $createDate, length = $length, hash function = ${hashFunction.getClass.getSimpleName} , secret = $secret, policy = $policy }"