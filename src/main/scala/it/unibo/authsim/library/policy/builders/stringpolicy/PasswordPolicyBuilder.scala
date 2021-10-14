package it.unibo.authsim.library.policy.builders.stringpolicy

import it.unibo.authsim.library.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.policy.builders.stringpolicy.StringPolicyBuildersHelpers
import it.unibo.authsim.library.policy.builders.stringpolicy.StringPolicyBuilder.AbstractMoreRestrictStringPolicyBuilder
import it.unibo.authsim.library.policy.model.StringPolicies.PasswordPolicy

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
 * ''PasswordPolicyBuilder'' is password policy builder
 */
case class PasswordPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[PasswordPolicy] :
  override def build: PasswordPolicy = new PasswordPolicy :
    override def minimumLength: Int = PasswordPolicyBuilder.this.minLen

    override def maximumLength: Option[Int] = PasswordPolicyBuilder.this.maxLen

    override def alphabet: PolicyAlphabet = PasswordPolicyBuilder.this.alphabetPolicy

    override def patterns: ListBuffer[Regex] = PasswordPolicyBuilder.this.patterns

    override def minimumUpperChars: Option[Int] = PasswordPolicyBuilder.this.minUpperChars

    override def minimumLowerChars: Option[Int] = PasswordPolicyBuilder.this.minLowerChars

    override def minimumSymbols: Option[Int] = PasswordPolicyBuilder.this.minSymbols

    override def minimumNumbers: Option[Int] = PasswordPolicyBuilder.this.minNumbers

    override def toString: String = StringPolicyBuildersHelpers.buildToString("PasswordPolicy", this)
