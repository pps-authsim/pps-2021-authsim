package it.unibo.authsim.library.dsl.otp.builders

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.AbstractHOTPBuilder
import it.unibo.authsim.library.dsl.otp.checkers.OTPChecker
import it.unibo.authsim.library.dsl.otp.generators.OTPGenerator
import it.unibo.authsim.library.dsl.otp.model.HOTP
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.generatorSeed
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

case class HOTPBuilder() extends AbstractHOTPBuilder:
  override def build: HOTP = new HOTP:

    override def length: Int = HOTPBuilder.this._length

    override def hashFunction: HashFunction = HOTPBuilder.this._hashFunction

    override def policy: OTPPolicy = HOTPBuilder.this._policy

    override def secret: String = HOTPBuilder.this._secret

    override def generate: String = implicitly[(HOTP, Int) => OTPGenerator].apply(this, HOTPBuilder.this._seed).generate

    override def check(pincode: String): Boolean = implicitly[(HOTP, Int) => OTPChecker].apply(this, HOTPBuilder.this._seed).check(pincode)

    override def reset: Unit = HOTPBuilder.this.generateSeed

    override def toString: String = s"HOTP = { length: $length, hash function = ${hashFunction.getClass.getSimpleName} , secret = $secret, policy = $policy }"
