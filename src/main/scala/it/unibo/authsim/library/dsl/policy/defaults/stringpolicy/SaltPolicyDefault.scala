package it.unibo.authsim.library.dsl.policy.defaults.stringpolicy

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.SaltPolicyBuilder
import it.unibo.authsim.library.dsl.policy.defaults.stringpolicy.PolicyAlphabets.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.SaltPolicy

import scala.language.postfixOps

object SaltPolicyDefault:
  /**
   * Salt Policy with minimum length of 3 characters and maximum of 7 characters.
   * 
   * The setted Alphabet is [[PolicyAlphabets.simpleAlphabet]]
   */
  val SIMPLE: SaltPolicy = SaltPolicyBuilder() addAlphabet simpleAlphabet minimumLength 3 maximumLength 7 build
  /**
   * Salt Policy with minimum length of 5 characters and at least 1 symbols.
   * 
   * The setted Alphabet is [[PolicyDefaultAlphabet PolicyDefaultAlphabet]]
   */
  val MEDIUM: SaltPolicy = SaltPolicyBuilder() minimumLength 5 minimumSymbols 1 build
  /**
   * Salt Policy with minimum length of 8 characters and at least 1 symbols and 1 upper characters.
   * 
   * The setted Alphabet is [[PolicyDefaultAlphabet PolicyDefaultAlphabet]]
   */
  val HARD: SaltPolicy = SaltPolicyBuilder() minimumLength 8 minimumSymbols 1 minimumUpperChars 1 build
  /**
   * Salt Policy with minimum length of 10 characters and at least 2 symbols and 3 upper characters.
   * 
   * The setted Alphabet is [[PolicyDefaultAlphabet PolicyDefaultAlphabet]]
   */
  val SUPER_HARD_HARD: SaltPolicy = SaltPolicyBuilder() minimumLength 10 minimumSymbols 2 minimumUpperChars 3 build
