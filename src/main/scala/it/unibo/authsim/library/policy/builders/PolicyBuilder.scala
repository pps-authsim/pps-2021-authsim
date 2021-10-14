package it.unibo.authsim.library.policy.builders

import it.unibo.authsim.library.Protocol
import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, SaltPolicy, UserIDPolicy}
import it.unibo.authsim.library.policy.model.Policy
import it.unibo.authsim.library.builder.Builder
import it.unibo.authsim.library.cryptography.algorithm.CryptographicAlgorithm

/**
 * ''PolicyBuilder'' is a trait that is used to build a new policy
 */
trait PolicyBuilder extends Builder[Policy]:
  /**
   * Set a [[UserIDPolicy userId policy]] and a [[PasswordPolicy password policy]]
   * @param credentialPolicy tuple of userID policy and a password policy to set
   * @return instance of policy builder
   */
  def of(credentialPolicy: (UserIDPolicy, PasswordPolicy)): this.type
  /**
   * Set a [[CredentialPolicy credential policy]]
   * @param credentialPolicy credential policy to set
   * @return instance of policy builder
   */
  def of(credentialPolicy: CredentialPolicy): this.type
  /**
   * Set a [[CredentialPolicy credential policy]]
   * @param credentialPolicy credential policy to set
   * @return instance of policy builder
   */
  def and(credentialPolicy: CredentialPolicy): this.type
  /**
   * Set a [[Protocol protocol]]
   * @param protocol protocol to set
   * @return instance of policy builder
   */
  def transmitWith(protocol: Protocol): this.type
  /**
   * Set a [[CryptographicAlgorithm cryptographic algorithm]]
   * @param cryptographicAlgorithm cryptographic algorithm to set
   * @return instance of policy builder
   */
  def storeWith(cryptographicAlgorithm: CryptographicAlgorithm): this.type
  /**
   * Set a [[CryptographicAlgorithm cryptographic algorithm]] and [[SaltPolicy salt policy]]
   * @param cryptographicAlgorithmSalted tuple of cryptographic algorithm and salt policy to set
   * @return instance of policy builder
   */
  def storeWith(cryptographicAlgorithmSalted: (CryptographicAlgorithm, SaltPolicy)): this.type

/**
 *  PolicyBuilder is an implementation of policy builder
 */
object PolicyBuilder:
  def apply(): PolicyBuilder = new PolicyBuilderImpl();
  def apply(policyName: String): PolicyBuilder = new PolicyBuilderImpl(policyName);

  private class PolicyBuilderImpl(private val name: String = "") extends PolicyBuilder:
    private var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty
    private var _protocol: Option[Protocol] = Option.empty
    private var _cryptographicAlgorithm: Option[CryptographicAlgorithm] = Option.empty
    private var _saltPolicy: Option[SaltPolicy] = Option.empty

    override def of(credentialPolicy: (UserIDPolicy, PasswordPolicy)) = this of credentialPolicy._1 and credentialPolicy._2

    override def of(credentialPolicy: CredentialPolicy) = this.builderMethod((credentialPolicy: CredentialPolicy) => this._credentialPolicies = credentialPolicy +: this._credentialPolicies)(credentialPolicy)

    override def and(credentialPolicy: CredentialPolicy) = this of credentialPolicy

    override def transmitWith(protocol: Protocol) = this.builderMethod((protocol: Protocol) => this._protocol = Some(protocol))(protocol)

    override def storeWith(cryptographicAlgorithm: CryptographicAlgorithm) = this.builderMethod((cryptographicAlgorithm: CryptographicAlgorithm) => this._cryptographicAlgorithm = Some(cryptographicAlgorithm))(cryptographicAlgorithm)

    override def storeWith(cryptographicAlgorithmSalted: (CryptographicAlgorithm, SaltPolicy)) =
      this.builderMethod((cryptographicAlgorithmSalted: (CryptographicAlgorithm, SaltPolicy)) => {
        this._cryptographicAlgorithm = Some(cryptographicAlgorithmSalted._1)
        this._saltPolicy = Some(cryptographicAlgorithmSalted._2)
      })(cryptographicAlgorithmSalted)

    override def build: Policy = new Policy:

      override def name: String = PolicyBuilderImpl.this.name

      override def credentialPolicies: Seq[CredentialPolicy] = PolicyBuilderImpl.this._credentialPolicies

      override def cryptographicAlgorithm: Option[CryptographicAlgorithm] = PolicyBuilderImpl.this._cryptographicAlgorithm

      override def saltPolicy: Option[SaltPolicy] = PolicyBuilderImpl.this._saltPolicy

      override def transmissionProtocol: Option[Protocol] = PolicyBuilderImpl.this._protocol

      override def toString: String =
        s"${name}Policy {" +
          s"Protocol = $transmissionProtocol, " +
          s"CryptographicAlgorithm = $cryptographicAlgorithm, SaltPolicy = $saltPolicy, " +
          s"CredentialPolicies = $credentialPolicies }"
