package it.unibo.authsim.library.dsl.policy.defaults.stringpolicy

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.OTPPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import scala.language.postfixOps

object OTPPolicyDefault:

  /**
   * OTP Policy with minimum length of 4 numeric characters and maximum of 20 (default of OTPPolicyBuilder)
   */
  val SIMPLE: OTPPolicy = OTPPolicyBuilder() minimumLength 4 build
  /**
   * OTP Policy with minimum length of 7 numeric characters and maximum of 20 (default of OTPPolicyBuilder)
   */
  val MEDIUM: OTPPolicy = OTPPolicyBuilder() minimumLength 7 build
  /**
   * OTP Policy with minimum length of 15 numeric characters and maximum of 20 (default of OTPPolicyBuilder)
   */
  val HARD: OTPPolicy = OTPPolicyBuilder() minimumLength 15 build
