package it.unibo.authsim.library.dsl.policy.defaults.stringpolicy

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.PasswordPolicyBuilder
import it.unibo.authsim.library.dsl.policy.defaults.stringpolicy.PolicyAlphabets.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.PasswordPolicy

import scala.language.postfixOps

object PasswordPolicyDefault:
  /**
   * Password Policy with minimum length of 3 characters and maximum of 7 characters.
   * 
   * The setted Alphabet is [[PolicyAlphabets.superSimpleAlphabet]]
   */
  val SIMPLE: PasswordPolicy = PasswordPolicyBuilder() addAlphabet superSimpleAlphabet minimumLength 3 maximumLength 7 build
  /**
   * Password Policy with minimum length of 8 characters.
   * 
   * The setted Alphabet is [[PolicyAlphabets.mediumAlphabet]]
   */
  val MEDIUM: PasswordPolicy = PasswordPolicyBuilder() addAlphabet mediumAlphabet minimumLength 8 build
  /**
   * Password Policy with minimum length of 8 characters and at least 1 symbols.
   * 
   * The setted Alphabet is [[PolicyDefaultAlphabet PolicyDefaultAlphabet]]
   */
  val HARD: PasswordPolicy = PasswordPolicyBuilder() minimumLength 8 minimumSymbols 1 build
  /**
   * Password Policy with minimum length of 8 characters and at least 1 symbols and 3 upper characters.
   * 
   * The setted Alphabet is [[PolicyDefaultAlphabet PolicyDefaultAlphabet]]
   */
  val SUPER_HARD: PasswordPolicy = PasswordPolicyBuilder() minimumLength 8 minimumSymbols 1 minimumUpperChars 3 build
  /**
   * Password Policy with minimum length of 10 characters and at least 2 symbols and 3 upper characters.
   * 
   * The setted Alphabet is [[PolicyDefaultAlphabet PolicyDefaultAlphabet]]
   */
  val SUPER_HARD_HARD: PasswordPolicy = PasswordPolicyBuilder() minimumLength 10 minimumSymbols 2 minimumUpperChars 3 build
