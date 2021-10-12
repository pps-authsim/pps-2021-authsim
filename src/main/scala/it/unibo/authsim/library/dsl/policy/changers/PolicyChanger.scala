package it.unibo.authsim.library.dsl.policy.changers

import it.unibo.authsim.library.dsl.Protocol
import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder.BasicPolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.*
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

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
    policy.credentialPolicies foreach { this.of(_) }
    if policy.transmissionProtocol.isDefined then this.transmitWith(policy.transmissionProtocol.get)
    if policy.cryptographicAlgorithm.isDefined && policy.saltPolicy.isDefined then
      this.storeWith((policy.cryptographicAlgorithm.get, policy.saltPolicy.get))
    else if policy.cryptographicAlgorithm.isDefined then
      this.storeWith(policy.cryptographicAlgorithm.get)
    override def build: Policy = policy

    override def rebuild: Policy = super.build

  /**
   * @param userIDPolicy userID policy to change
   * @return a instance of [[UserIDPolicyChanger]]
   */
  def userID(userIDPolicy: UserIDPolicy) = new UserIDPolicyChanger:
    require(userIDPolicy != null, "userID policy must be initialized")
    this.addAlphabet(userIDPolicy.alphabet)
    this.minimumLength(userIDPolicy.minimumLength)
    if userIDPolicy.maximumLength.isDefined then this.maximumLength(userIDPolicy.maximumLength.get)
    if userIDPolicy.minimumLowerChars.isDefined then this.minimumLowerChars(userIDPolicy.minimumLowerChars.get)
    if userIDPolicy.minimumUpperChars.isDefined then this.minimumUpperChars(userIDPolicy.minimumUpperChars.get)
    if userIDPolicy.minimumNumbers.isDefined then this.minimumNumbers(userIDPolicy.minimumNumbers.get)
    if userIDPolicy.minimumSymbols.isDefined then this.minimumSymbols(userIDPolicy.minimumSymbols.get)
    override def build: UserIDPolicy = userIDPolicy

    override def rebuild: UserIDPolicy = super.build


  /**
   * @param passwordPolicy password policy to change
   * @return a instance of [[PasswordPolicyChanger]]
   */
  def password(passwordPolicy: PasswordPolicy) = new PasswordPolicyChanger:
    require(passwordPolicy != null, "password policy must be initialized")
    this.addAlphabet(passwordPolicy.alphabet)
    this.minimumLength(passwordPolicy.minimumLength)
    if passwordPolicy.maximumLength.isDefined then this.maximumLength(passwordPolicy.maximumLength.get)
    if passwordPolicy.minimumLowerChars.isDefined then this.minimumLowerChars(passwordPolicy.minimumLowerChars.get)
    if passwordPolicy.minimumUpperChars.isDefined then this.minimumUpperChars(passwordPolicy.minimumUpperChars.get)
    if passwordPolicy.minimumNumbers.isDefined then this.minimumNumbers(passwordPolicy.minimumNumbers.get)
    if passwordPolicy.minimumSymbols.isDefined then this.minimumSymbols(passwordPolicy.minimumSymbols.get)
    override def build: PasswordPolicy = passwordPolicy

    override def rebuild: PasswordPolicy = super.build


  /**
   * @param otpPolicy opt policy to change
   * @return a instance of [[OTPPolicyChanger]]
   */
  def otp(otpPolicy: OTPPolicy) = new OTPPolicyChanger:
    require(otpPolicy != null, "OTP policy must be initialized")
    this.setAlphabet(otpPolicy.alphabet)
    this.minimumLength(otpPolicy.minimumLength)
    if otpPolicy.maximumLength.isDefined then this.maximumLength(otpPolicy.maximumLength.get)
    override def build: OTPPolicy = otpPolicy

    override def rebuild: OTPPolicy = super.build

  /**
   * @param saltPolicy salt policy to change
   * @return a instance of [[SaltPolicyChanger]]
   */
  def salt(saltPolicy: SaltPolicy) = new SaltPolicyChanger:
    require(saltPolicy != null, "salt policy must be initialized")
    this.addAlphabet(saltPolicy.alphabet)
    this.minimumLength(saltPolicy.minimumLength)
    if saltPolicy.maximumLength.isDefined then this.maximumLength(saltPolicy.maximumLength.get)
    if saltPolicy.minimumLowerChars.isDefined then this.minimumLowerChars(saltPolicy.minimumLowerChars.get)
    if saltPolicy.minimumUpperChars.isDefined then this.minimumUpperChars(saltPolicy.minimumUpperChars.get)
    if saltPolicy.minimumNumbers.isDefined then this.minimumNumbers(saltPolicy.minimumNumbers.get)
    if saltPolicy.minimumSymbols.isDefined then this.minimumSymbols(saltPolicy.minimumSymbols.get)
    override def build: SaltPolicy = saltPolicy

    override def rebuild: SaltPolicy = super.build

