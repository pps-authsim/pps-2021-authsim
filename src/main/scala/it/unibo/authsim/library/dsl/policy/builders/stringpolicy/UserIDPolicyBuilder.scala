package it.unibo.authsim.library.dsl.policy.builders.stringpolicy

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.StringPolicyBuildersHelpers
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.StringPolicyBuilder.AbstractMoreRestrictStringPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.UserIDPolicy

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
 * ''UserIDPolicyBuilder'' is userID policy builder
 */
case class UserIDPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[UserIDPolicy] :
  override def build: UserIDPolicy = new UserIDPolicy :
    override def minimumLength: Int = UserIDPolicyBuilder.this.minLen

    override def maximumLength: Option[Int] = UserIDPolicyBuilder.this.maxLen

    override def alphabet: PolicyAlphabet = UserIDPolicyBuilder.this.alphabetPolicy

    override def patterns: ListBuffer[Regex] = UserIDPolicyBuilder.this.patterns

    override def minimumUpperChars: Option[Int] = UserIDPolicyBuilder.this.minUpperChars

    override def minimumLowerChars: Option[Int] = UserIDPolicyBuilder.this.minLowerChars

    override def minimumSymbols: Option[Int] = UserIDPolicyBuilder.this.minSymbols

    override def minimumNumbers: Option[Int] = UserIDPolicyBuilder.this.minNumbers

    override def toString: String = StringPolicyBuildersHelpers.buildToString("UseIDPolicy", this)
