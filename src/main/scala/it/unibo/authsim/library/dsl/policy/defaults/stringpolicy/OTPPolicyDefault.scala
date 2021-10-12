package it.unibo.authsim.library.dsl.policy.defaults.stringpolicy

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyOTPAlphabet
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.OTPPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import scala.language.postfixOps

object OTPPolicyDefault:

  /**
   * OTP Policy with minimum length of 4 digits and maximum of 6 digits.
   *
   * The setted Alphabet is [[PolicyOTPAlphabet PolicyOTPAlphabet]]
   */
  val SIMPLE: OTPPolicy = OTPPolicyBuilder() minimumLength 4 maximumLength 6 build
  /**
   * OTP Policy with minimum length of 7 digits.
   *
   * The setted Alphabet is [[PolicyOTPAlphabet PolicyOTPAlphabet]]
   */
  val MEDIUM: OTPPolicy = OTPPolicyBuilder() minimumLength 7 build
  /**
   * OTP Policy with minimum length of 15 digits.
   *
   * The setted Alphabet is [[PolicyOTPAlphabet PolicyOTPAlphabet]]
   */
  val HARD: OTPPolicy = OTPPolicyBuilder() minimumLength 15 build
