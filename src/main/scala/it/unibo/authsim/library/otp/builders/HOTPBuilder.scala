package it.unibo.authsim.library.otp.builders

import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.otp.builders.OTPBuilder.AbstractHOTPBuilder
import it.unibo.authsim.library.otp.model.HOTP
import it.unibo.authsim.library.policy.model.StringPolicies.OTPPolicy

/**
 * ''TOTPBuilder'' is the Hash Message Authentication Codes based One-Time Password (HOTP) builder.
 */
case class HOTPBuilder() extends AbstractHOTPBuilder[HOTP]:
  override def build: HOTP = new HOTP:

    override def length: Int = HOTPBuilder.this._length

    override def hashFunction: HashFunction = HOTPBuilder.this._hashFunction

    override def policy: OTPPolicy = HOTPBuilder.this._policy

    override def generate: String = HOTPBuilder.this.otpGenerator()

    override def check(pincode: String): Boolean = HOTPBuilder.this.checked(this.generate == pincode)

    override def reset: Unit = HOTPBuilder.this.reset

    override def toString: String = s"HOTP = { length = $length, hash function = ${hashFunction.getClass.getSimpleName} , secret = $secret, policy = $policy }"
