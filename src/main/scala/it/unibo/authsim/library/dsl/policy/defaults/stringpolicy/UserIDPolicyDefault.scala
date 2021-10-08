package it.unibo.authsim.library.dsl.policy.defaults.stringpolicy

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.UserIDPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.UserIDPolicy

import scala.language.postfixOps

object UserIDPolicyDefault:
  /**
   * UserID Policy with minimum length of 3 characters (alphanumeric + some symbols) and maximum of 6 characters (alphanumeric + some symbols)
   */
  val SUPER_SIMPLE: UserIDPolicy = UserIDPolicyBuilder() minimumLength 3 maximumLength 6 build
  /**
   * UserID Policy with minimum length of 8 (alphanumeric + some symbols) and maximum of
   */
  val SIMPLE: UserIDPolicy = UserIDPolicyBuilder() minimumLength 8 build
  /**
   * UserID Policy with minimum length of 8 (alphanumeric + some symbols) and at least 1 symbols
   */
  val MEDIUM: UserIDPolicy = UserIDPolicyBuilder() minimumLength 8 minimumSymbols 1 build
  /**
   * UserID Policy with minimum length of 10 (alphanumeric + some symbols) and at least 2 symbols and 3 upper characters
   */
  val HARD: UserIDPolicy = UserIDPolicyBuilder() minimumLength 10 minimumSymbols 2 minimumUpperChars 3 build
