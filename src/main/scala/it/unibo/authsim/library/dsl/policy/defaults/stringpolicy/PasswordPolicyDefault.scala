package it.unibo.authsim.library.dsl.policy.defaults.stringpolicy

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.PasswordPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.PasswordPolicy

import scala.language.postfixOps

object PasswordPolicyDefault:
  /**
   * Password Policy with minimum length of 3 characters (alphanumeric + some symbols)
   */
  val SIMPLE: PasswordPolicy = PasswordPolicyBuilder() minimumLength 3 build
  /**
   * Password Policy with minimum length of 8 characters (alphanumeric + some symbols) 
   */
  val MEDIUM: PasswordPolicy = PasswordPolicyBuilder() minimumLength 8 build
  /**
   * Password Policy with minimum length of 8 characters (alphanumeric + some symbols) 
   * and at least 1 symbols
   */
  val HARD: PasswordPolicy = PasswordPolicyBuilder() minimumLength 8 minimumSymbols 1 build
  /**
   * Password Policy with minimum length of 8 characters (alphanumeric + some symbols) 
   * and at least 1 symbols and 3 upper characters
   */
  val SUPER_HARD: PasswordPolicy = PasswordPolicyBuilder() minimumLength 8 minimumSymbols 1 minimumUpperChars 3 build
  /**
   * Password Policy with minimum length of 10 characters (alphanumeric + some symbols)
   * and at least 2 symbols and 3 upper characters
   */
  val SUPER_HARD_HARD: PasswordPolicy = PasswordPolicyBuilder() minimumLength 10 minimumSymbols 2 minimumUpperChars 3 build
