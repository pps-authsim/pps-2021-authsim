package it.unibo.authsim.library.otp.generators

import it.unibo.authsim.library.policy.model.StringPolicies.OTPPolicy

import scala.util.Random

/**
 * ''LengthGenerator'' is a trait used to generate an actual length of OTP.
 */
trait LengthGenerator:
  /**
   * @param policy otp policy used to generate an actual length to set
   * @return an actual length of OTP.
   */
  def length(policy: OTPPolicy): Int

object LengthGenerator:
  /**
   * A default implementation of an length generator.
   */
  implicit val generatorLength: LengthGenerator = new LengthGenerator:
    override def length(policy: OTPPolicy): Int = Random.between(policy.minimumLength, policy.maximumLength.getOrElse(policy.minimumLength + 10) + 1)