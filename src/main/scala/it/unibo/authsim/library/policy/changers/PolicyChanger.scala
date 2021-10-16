package it.unibo.authsim.library.dsl.policy.changers

import it.unibo.authsim.library.Protocol
import it.unibo.authsim.library.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.policy.builders.PolicyBuilder.BasicPolicyBuilder
import it.unibo.authsim.library.policy.builders.stringpolicy.*
import it.unibo.authsim.library.policy.model.Policy
import it.unibo.authsim.library.policy.model.StringPolicies.*

/**
 * A ''PolicyChanger'' is a trait that is used to redefine a policy
 * @tparam T value of the policy
 */
trait PolicyChanger[T]:
  /**
   * @return an instance of new policy
   */
  def rebuild: T

object PolicyChanger:

  /**
   * ''UserIDPolicyChanger'' is trait that is used to create a PolicyChanger for [[UserIDPolicy userID policy]]
   */
  trait UserIDPolicyChanger extends UserIDPolicyBuilder with PolicyChanger[UserIDPolicy]
  /**
   * ''PasswordPolicyChanger'' is trait that is used to create a PolicyChanger for [[PasswordPolicy password policy]]
   */
  trait PasswordPolicyChanger extends PasswordPolicyBuilder with PolicyChanger[PasswordPolicy]
  /**
   * ''OTPPolicyChanger'' is trait that is used to create a PolicyChanger for [[OTPPolicy otp policy]]
   */
  trait OTPPolicyChanger extends OTPPolicyBuilder with PolicyChanger[OTPPolicy]
  /**
   * ''SaltPolicyChanger'' is trait that is used to create a PolicyChanger for [[SaltPolicy salt policy]]
   */
  trait SaltPolicyChanger extends SaltPolicyBuilder with PolicyChanger[SaltPolicy]


  /**
   * @param policy policy to change
   * @return a instance of Policy Changer for [[Policy Policy]]
   */
  def apply(policy: Policy) = new BasicPolicyBuilder(Some(policy.name)) with PolicyChanger[Policy]:
    require(policy != null, "policy must be initialized")
    this.init(policy)
    override def build: Policy = policy
    override def rebuild: Policy = super.build

  /**
   * @param userIDPolicy userID policy to change
   * @return a instance of [[UserIDPolicyChanger]]
   */
  def userID(userIDPolicy: UserIDPolicy) = new UserIDPolicyChanger:
    require(userIDPolicy != null, "userID policy must be initialized")
    this.init(userIDPolicy)
    override def build: UserIDPolicy = userIDPolicy
    override def rebuild: UserIDPolicy = super.build


  /**
   * @param passwordPolicy password policy to change
   * @return a instance of [[PasswordPolicyChanger]]
   */
  def password(passwordPolicy: PasswordPolicy) = new PasswordPolicyChanger:
    require(passwordPolicy != null, "password policy must be initialized")
    this.init(passwordPolicy)
    override def build: PasswordPolicy = passwordPolicy
    override def rebuild: PasswordPolicy = super.build


  /**
   * @param otpPolicy opt policy to change
   * @return a instance of [[OTPPolicyChanger]]
   */
  def otp(otpPolicy: OTPPolicy) = new OTPPolicyChanger:
    require(otpPolicy != null, "OTP policy must be initialized")
    this.init(otpPolicy)
    override def build: OTPPolicy = otpPolicy
    override def rebuild: OTPPolicy = super.build

  /**
   * @param saltPolicy salt policy to change
   * @return a instance of [[SaltPolicyChanger]]
   */
  def salt(saltPolicy: SaltPolicy) = new SaltPolicyChanger:
    require(saltPolicy != null, "salt policy must be initialized")
    this.init(saltPolicy)
    override def build: SaltPolicy = saltPolicy
    override def rebuild: SaltPolicy = super.build

