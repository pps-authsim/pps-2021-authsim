package it.unibo.authsim.library.otp.model

import it.unibo.authsim.library.policy.model.StringPolicies.OTPPolicy

/**
 * ''OTP'' is a trait used to define an one time password
 */
trait OTP:
  /**
   * @return an otp policy
   */
  def policy: OTPPolicy
  /**
   * @return actual length of otp
   */
  def length: Int
  /**
   * Reset the one time password so that when the method [[OTP.generate]] is invoked it returns a different value
   */
  def reset: Unit
  /**
   * @return a generated valid string that rappresent the one time password
   */
  def generate: String
  /**
   * Check if the given value is valid as an one time password
   * @param pincode value to check
   * @return true if the given ''value'' is a valid value as an one time password, otherwise false
   */
  def check(pincode: String): Boolean
