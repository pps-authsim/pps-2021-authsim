package it.unibo.authsim.library.dsl.otp.builders

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.AbstractHOTPBuilder
import it.unibo.authsim.library.dsl.otp.checkers.OTPChecker
import it.unibo.authsim.library.dsl.otp.generators.OTPGenerator
import it.unibo.authsim.library.dsl.otp.model.HOTP
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

case class HOTPBuilder() extends AbstractHOTPBuilder:
  override def build: HOTP = new HOTP:

    override def hashFunction: HashFunction = HOTPBuilder.this._hashFunction

    override def policy: OTPPolicy = HOTPBuilder.this._policy

    override def secret: String = HOTPBuilder.this._secret

    override def generate: String = implicitly[HOTP => OTPGenerator].apply(this).generate

    override def check(pincode: String): Boolean = implicitly[HOTP => OTPChecker].apply(this).check(pincode)

    override def toString: String = s"HOTP = { hash function = ${hashFunction.getClass.getSimpleName} , secret = $secret, policy = $policy }"
