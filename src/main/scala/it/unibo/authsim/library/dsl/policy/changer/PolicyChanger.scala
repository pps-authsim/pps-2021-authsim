package it.unibo.authsim.library.dsl.policy.changer

import it.unibo.authsim.library.dsl.Protocol
import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.*
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

trait PolicyChanger[T]:
  def rebuild: T

object PolicyChanger:

  trait UserIDPolicyChanger extends UserIDPolicyBuilder with PolicyChanger[UserIDPolicy]
  trait PasswordPolicyChanger extends PasswordPolicyBuilder with PolicyChanger[PasswordPolicy]
  trait OTPPolicyChanger extends OTPPolicyBuilder with PolicyChanger[OTPPolicy]
  trait SaltPolicyChanger extends SaltPolicyBuilder with PolicyChanger[SaltPolicy]


  def apply(policy: Policy) = new PolicyBuilder with PolicyChanger[Policy]:
    require(policy != null, "policy must be initialized")

    private val builder: PolicyBuilder = policy.name match
      case "" => PolicyBuilder()
      case _ => PolicyBuilder(policy.name)

    policy.credentialPolicies foreach { builder.of(_) }

    if policy.transmissionProtocol.isDefined then builder.transmitWith(policy.transmissionProtocol.get)

    if policy.cryptographicAlgorithm.isDefined && policy.saltPolicy.isDefined then
      builder.storeWith((policy.cryptographicAlgorithm.get, policy.saltPolicy.get))
    else if policy.cryptographicAlgorithm.isDefined then
      builder.storeWith(policy.cryptographicAlgorithm.get)

    override def of(credentialPolicy: (UserIDPolicy, PasswordPolicy)): this.type = this.builderMethod(credentialPolicy => builder.of(credentialPolicy))(credentialPolicy)
    override def of(credentialPolicy: CredentialPolicy): this.type = this.builderMethod(credentialPolicy => builder.of(credentialPolicy))(credentialPolicy)
    override def and(credentialPolicy: CredentialPolicy): this.type = this.builderMethod(credentialPolicy => builder.and(credentialPolicy))(credentialPolicy)
    override def transmitWith(protocol: Protocol): this.type = this.builderMethod(protocol => builder.transmitWith(protocol))(protocol)
    override def storeWith(cryptographicAlgorithm: CryptographicAlgorithm): this.type = this.builderMethod(cryptographicAlgorithm => builder.storeWith(cryptographicAlgorithm))(cryptographicAlgorithm)
    override def storeWith(cryptographicAlgorithmSalted: (CryptographicAlgorithm, SaltPolicy)): this.type = this.builderMethod(cryptographicAlgorithmSalted => builder.storeWith(cryptographicAlgorithmSalted))(cryptographicAlgorithmSalted)
    override def build: Policy = policy

    override def rebuild: Policy = builder.build

  def userID(userIDPolicy: UserIDPolicy) = new UserIDPolicyChanger:
    require(userIDPolicy != null, "userID policy must be initialized")
    this.addAlphabet(userIDPolicy.alphabet)
    this.minimumLength(userIDPolicy.minimumLength)
    if userIDPolicy.maximumLength.isDefined then this.maximumLength(userIDPolicy.maximumLength.get)
    if userIDPolicy.minimumLowerChars.isDefined then this.minimumLowerChars(userIDPolicy.minimumLowerChars.get)
    if userIDPolicy.minimumUpperChars.isDefined then this.minimumUpperChars(userIDPolicy.minimumUpperChars.get)
    if userIDPolicy.minimumNumbers.isDefined then this.minimumNumbers(userIDPolicy.minimumNumbers.get)
    if userIDPolicy.minimumSymbols.isDefined then this.minimumSymbols(userIDPolicy.minimumSymbols.get)
    override def rebuild: UserIDPolicy = this.build

  def password(passwordPolicy: PasswordPolicy) = new PasswordPolicyChanger:
    require(passwordPolicy != null, "password policy must be initialized")
    this.addAlphabet(passwordPolicy.alphabet)
    this.minimumLength(passwordPolicy.minimumLength)
    if passwordPolicy.maximumLength.isDefined then this.maximumLength(passwordPolicy.maximumLength.get)
    if passwordPolicy.minimumLowerChars.isDefined then this.minimumLowerChars(passwordPolicy.minimumLowerChars.get)
    if passwordPolicy.minimumUpperChars.isDefined then this.minimumUpperChars(passwordPolicy.minimumUpperChars.get)
    if passwordPolicy.minimumNumbers.isDefined then this.minimumNumbers(passwordPolicy.minimumNumbers.get)
    if passwordPolicy.minimumSymbols.isDefined then this.minimumSymbols(passwordPolicy.minimumSymbols.get)
    override def rebuild: PasswordPolicy = this.build

  def otp(otpPolicy: OTPPolicy) = new OTPPolicyChanger:
    require(otpPolicy != null, "OTP policy must be initialized")
    this.setAlphabet(otpPolicy.alphabet)
    this.minimumLength(otpPolicy.minimumLength)
    if otpPolicy.maximumLength.isDefined then this.maximumLength(otpPolicy.maximumLength.get)
    override def rebuild: OTPPolicy = this.build

  def salt(saltPolicy: SaltPolicy) = new SaltPolicyChanger:
    require(saltPolicy != null, "salt policy must be initialized")
    this.addAlphabet(saltPolicy.alphabet)
    this.minimumLength(saltPolicy.minimumLength)
    if saltPolicy.maximumLength.isDefined then this.maximumLength(saltPolicy.maximumLength.get)
    if saltPolicy.minimumLowerChars.isDefined then this.minimumLowerChars(saltPolicy.minimumLowerChars.get)
    if saltPolicy.minimumUpperChars.isDefined then this.minimumUpperChars(saltPolicy.minimumUpperChars.get)
    if saltPolicy.minimumNumbers.isDefined then this.minimumNumbers(saltPolicy.minimumNumbers.get)
    if saltPolicy.minimumSymbols.isDefined then this.minimumSymbols(saltPolicy.minimumSymbols.get)
    override def rebuild: SaltPolicy = this.build

