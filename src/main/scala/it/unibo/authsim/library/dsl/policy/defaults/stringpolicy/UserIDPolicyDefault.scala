package it.unibo.authsim.library.dsl.policy.defaults.stringpolicy

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.UserIDPolicyBuilder
import it.unibo.authsim.library.dsl.policy.defaults.stringpolicy.PolicyAlphabets.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.UserIDPolicy

import scala.language.postfixOps

object UserIDPolicyDefault:
  /**
   * UserID Policy with minimum length of 3 characters and maximum of 6 characters.
   * 
   * The setted Alphabet is [[PolicyAlphabets.superSimpleAlphabet]]
   */
  val SUPER_SIMPLE: UserIDPolicy = UserIDPolicyBuilder() addAlphabet superSimpleAlphabet minimumLength 3 maximumLength 6 build
  /**
   * UserID Policy with minimum length of 8 characters.
   * 
   * The setted Alphabet is [[PolicyAlphabets.simpleAlphabet]]
   */
  val SIMPLE: UserIDPolicy = UserIDPolicyBuilder() addAlphabet simpleAlphabet minimumLength 8 build
  /**
   * UserID Policy with minimum length of 8 and at least 1 symbols.
   * 
   * The setted Alphabet is [[PolicyDefaultAlphabet PolicyDefaultAlphabet]]
   */
  val MEDIUM: UserIDPolicy = UserIDPolicyBuilder() minimumLength 8 minimumSymbols 1 build
  /**
   * UserID Policy with minimum length of 10 and at least 2 symbols and 3 upper characters.
   * 
   * The setted Alphabet is [[PolicyDefaultAlphabet PolicyDefaultAlphabet]]
   */
  val HARD: UserIDPolicy = UserIDPolicyBuilder() minimumLength 10 minimumSymbols 2 minimumUpperChars 3 build
