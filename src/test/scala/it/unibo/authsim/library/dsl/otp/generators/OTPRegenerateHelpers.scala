package it.unibo.authsim.library.dsl.otp.generators

import it.unibo.authsim.library.dsl.otp.model.OTP
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.OTPPolicyBuilder
import it.unibo.authsim.library.dsl.policy.defaults.stringpolicy.OTPPolicyDefault
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

import scala.language.postfixOps

object OTPRegenerateHelpers:
  val policySmall: OTPPolicy = OTPPolicyBuilder() maximumLength 3 build
  val policyMedium: OTPPolicy = OTPPolicyDefault.MEDIUM
  val policyLarge: OTPPolicy = OTPPolicyBuilder() maximumLength 70 minimumLength 50 build

  private var pincode: String = null

  def checkNewPincodes(otp: OTP): Unit =
    otp.generate
    (0 to 100_000).foreach(_ =>
      otp.reset
      val gen = otp.generate
      assert(pincode != gen)
      pincode = gen
    )