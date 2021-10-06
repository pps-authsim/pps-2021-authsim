package it.unibo.authsim.library.dsl.otp.builders

import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.AbstractHOTPBuilder
import it.unibo.authsim.library.dsl.otp.model.HOTP
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.generatorSeed
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

/**
 * ''TOTPBuilder'' is the Hash Message Authentication Codes based One-Time Password (HOTP) builder.
 */
case class HOTPBuilder() extends AbstractHOTPBuilder:
  override def build: HOTP = new HOTP:

    override def length: Int = HOTPBuilder.this._length

    override def hashFunction: HashFunction = HOTPBuilder.this._hashFunction

    override def policy: OTPPolicy = HOTPBuilder.this._policy

    override def generate: String = HOTPBuilder.this.otpGenerator()

    override def check(pincode: String): Boolean =  this.generate == pincode

    override def reset: Unit = HOTPBuilder.this.generateSeed

    override def toString: String = s"HOTP = { length = $length, hash function = ${hashFunction.getClass.getSimpleName} , secret = $secret, policy = $policy }"
