package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.{HashFunction, Protocol}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, SaltPolicy, UserIDPolicy}
import it.unibo.authsim.library.dsl.policy.model.Policy

/**
 * ''PolicyBuilder'' is a trait that is used to build a new policy
 */
trait PolicyBuilder extends Builder[Policy]:
  /**
   * Set a [[UserIDPolicy userId policy]] and a [[PasswordPolicy password policy]]
   * @param credentialPolicy tuple of userID policy and a password policy to set
   * @return instance of policy builder
   */
  def of(credentialPolicy: (UserIDPolicy, PasswordPolicy)): PolicyBuilder
  /**
   * Set a [[CredentialPolicy credential policy]]
   * @param credentialPolicy credential policy to set
   * @return instance of policy builder
   */
  def of(credentialPolicy: CredentialPolicy): PolicyBuilder
  /**
   * Set a [[CredentialPolicy credential policy]]
   * @param credentialPolicy credential policy to set
   * @return instance of policy builder
   */
  def and(credentialPolicy: CredentialPolicy): PolicyBuilder
  /**
   * Set a [[Protocol protocol]]
   * @param protocol protocol to set
   * @return instance of policy builder
   */
  def transmitWith(protocol: Protocol): PolicyBuilder
  /**
   * Set a [[HashFunction hash function]]
   * @param hashFunction hash function to set
   * @return instance of policy builder
   */
  def storeWith(hashFunction: HashFunction): PolicyBuilder
  /**
   * Set a [[HashFunction hash function]] and [[SaltPolicy salt policy]]
   * @param hashFunctionSalted tuple of hash function and salt policy to set
   * @return instance of policy builder
   */
  def storeWith(hashFunctionSalted: (HashFunction, SaltPolicy)): PolicyBuilder

/**
 *  PolicyBuilder is an implementation of policy builder
 */
object PolicyBuilder:
  def apply(): PolicyBuilder = new PolicyBuilderImpl();
  def apply(policyName: String): PolicyBuilder = new PolicyBuilderImpl(policyName);

  private class PolicyBuilderImpl(private val name: String = "") extends PolicyBuilder:
    private var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty
    private var _protocol: Option[Protocol] = Option.empty
    private var _hashFunction: Option[HashFunction] = Option.empty
    private var _saltPolicy: Option[SaltPolicy] = Option.empty

    override def of(credentialPolicy: (UserIDPolicy, PasswordPolicy)): PolicyBuilder =
      this.of(credentialPolicy._1)
      this.of(credentialPolicy._2)
      this

    override def of(credentialPolicy: CredentialPolicy): PolicyBuilder =
      this._credentialPolicies = credentialPolicy +: this._credentialPolicies
      this

    override def and(credentialPolicy: CredentialPolicy): PolicyBuilder =
      this.of(credentialPolicy)
      this

    override def transmitWith(protocol: Protocol): PolicyBuilder =
      this._protocol = Some(protocol)
      this

    override def storeWith(hashFunction: HashFunction): PolicyBuilder =
      this._hashFunction = Some(hashFunction)
      this

    override def storeWith(hashFunctionSalted: (HashFunction, SaltPolicy)): PolicyBuilder =
      this._hashFunction = Some(hashFunctionSalted._1)
      this._saltPolicy = Some(hashFunctionSalted._2)
      this

    override def build: Policy = new Policy:

      override def name: String = PolicyBuilderImpl.this.name

      override def credentialPolicies: Seq[CredentialPolicy] = PolicyBuilderImpl.this._credentialPolicies

      override def hashFunction: Option[HashFunction] = PolicyBuilderImpl.this._hashFunction

      override def saltPolicy: Option[SaltPolicy] = PolicyBuilderImpl.this._saltPolicy

      override def transmissionProtocol: Option[Protocol] = PolicyBuilderImpl.this._protocol

      override def toString: String =
        s"${name}Policy {" +
          s"Protocol = $transmissionProtocol, " +
          s"HashFunction = $hashFunction, SaltPolicy = $saltPolicy, " +
          s"CredentialPolicies = $credentialPolicies }"
