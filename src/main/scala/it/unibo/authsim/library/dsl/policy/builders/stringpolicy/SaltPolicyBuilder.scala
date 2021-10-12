package it.unibo.authsim.library.dsl.policy.builders.stringpolicy

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.StringPolicyBuilder.AbstractMoreRestrictStringPolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.StringPolicyBuildersHelpers
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.SaltPolicy

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
 * ''SaltPolicyBuilder'' is salt policy builder
 */
case class SaltPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[SaltPolicy] :
  override def build: SaltPolicy = new SaltPolicy :
    override def minimumLength: Int = SaltPolicyBuilder.this.minLen

    override def maximumLength: Option[Int] = SaltPolicyBuilder.this.maxLen

    override def alphabet: PolicyAlphabet = SaltPolicyBuilder.this.alphabetPolicy

    override def patterns: ListBuffer[Regex] = SaltPolicyBuilder.this.patterns

    override def minimumUpperChars: Option[Int] = SaltPolicyBuilder.this.minUpperChars

    override def minimumLowerChars: Option[Int] = SaltPolicyBuilder.this.minLowerChars

    override def minimumSymbols: Option[Int] = SaltPolicyBuilder.this.minSymbols

    override def minimumNumbers: Option[Int] = SaltPolicyBuilder.this.minNumbers

    override def toString: String = StringPolicyBuildersHelpers.buildToString("SaltPolicy", this)
