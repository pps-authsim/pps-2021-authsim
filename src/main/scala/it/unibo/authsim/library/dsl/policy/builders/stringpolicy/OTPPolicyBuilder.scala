package it.unibo.authsim.library.dsl.policy.builders.stringpolicy

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyOTPAlphabet
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.StringPolicyBuilder.AbstractStringPolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.StringPolicyBuildersHelpers
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import scala.util.matching.Regex

/**
 * ''OTPPolicyBuilder'' is OTP policy builder
 */
case class OTPPolicyBuilder() extends AbstractStringPolicyBuilder[OTPPolicy] :
  OTPPolicyBuilder.this.setAlphabet(PolicyOTPAlphabet())

  override def build: OTPPolicy = new OTPPolicy :
    override def minimumLength: Int = OTPPolicyBuilder.this.minLen

    override def maximumLength: Option[Int] = OTPPolicyBuilder.this.maxLen

    override def alphabet: PolicyAlphabet = OTPPolicyBuilder.this.alphabetPolicy

    override def patterns: Seq[Regex] = OTPPolicyBuilder.this.patterns.toSeq

    override def toString: String = StringPolicyBuildersHelpers.buildToString("OTPPolicy", this)
