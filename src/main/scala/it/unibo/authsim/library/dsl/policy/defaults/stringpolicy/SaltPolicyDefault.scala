package it.unibo.authsim.library.dsl.policy.defaults.stringpolicy

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.SaltPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.SaltPolicy

import scala.language.postfixOps

object SaltPolicyDefault:
  /**
   * Salt Policy with minimum length of 3 characters (alphanumeric + some symbols) and maximum of 7 characters (alphanumeric + some symbols)
   */
  val SIMPLE: SaltPolicy = SaltPolicyBuilder() minimumLength 3 maximumLength 7 build
  /**
   * Salt Policy with minimum length of 5 characters (alphanumeric + some symbols)
   * and at least 1 symbols
   */
  val MEDIUM: SaltPolicy = SaltPolicyBuilder() minimumLength 5 minimumSymbols 1 build
  /**
   * Salt Policy with minimum length of 8 characters (alphanumeric + some symbols)
   * and at least 1 symbols and 1 upper characters
   */
  val HARD: SaltPolicy = SaltPolicyBuilder() minimumLength 8 minimumSymbols 1 minimumUpperChars 1 build
  /**
   * Salt Policy with minimum length of 10 characters (alphanumeric + some symbols) 
   * and at least 2 symbols and 3 upper characters
   */
  val SUPER_HARD_HARD: SaltPolicy = SaltPolicyBuilder() minimumLength 10 minimumSymbols 2 minimumUpperChars 3 build
