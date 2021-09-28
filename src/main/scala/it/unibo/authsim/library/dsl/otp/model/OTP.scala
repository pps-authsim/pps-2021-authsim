package it.unibo.authsim.library.dsl.otp.model

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy

trait OTP:
  def policy: OTPPolicy
  def secret: String
  def generate: String
  def check(pincode: String): Boolean
