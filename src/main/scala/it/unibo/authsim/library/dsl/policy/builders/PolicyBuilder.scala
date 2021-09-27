package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.{HashFunction, Protocol}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, SaltPolicy, UserIDPolicy}
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.builder.Builder

trait PolicyBuilder extends Builder[Policy]:
  def of(credentialPolicy: (UserIDPolicy, PasswordPolicy)): PolicyBuilder
  def of(credentialPolicy: CredentialPolicy): PolicyBuilder
  def and(credentialPolicy: CredentialPolicy): PolicyBuilder
  def transmitWith(protocol: Protocol): PolicyBuilder
  def storeWith(hashFunction: HashFunction): PolicyBuilder
  def storeWith(hashFunctionSalted: (HashFunction, SaltPolicy)): PolicyBuilder

object PolicyBuilder:
  def apply(): PolicyBuilder = new PolicyBuilderImpl();
  def apply(policyName: String): PolicyBuilder = new PolicyBuilderImpl(policyName);

  private class PolicyBuilderImpl(private val name: String = "") extends PolicyBuilder:
    private var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty
    private var _protocol: Option[Protocol] = Option.empty
    private var _hashFunction: Option[HashFunction] = Option.empty
    private var _saltPolicy: Option[SaltPolicy] = Option.empty

    override def of(credentialPolicy: (UserIDPolicy, PasswordPolicy)): PolicyBuilder =
      this of credentialPolicy._1 and credentialPolicy._2

    override def of(credentialPolicy: CredentialPolicy): PolicyBuilder =
      this.builderMethod((credentialPolicy: CredentialPolicy) => this._credentialPolicies = credentialPolicy +: this._credentialPolicies)(credentialPolicy)

    override def and(credentialPolicy: CredentialPolicy): PolicyBuilder = this of credentialPolicy

    override def transmitWith(protocol: Protocol): PolicyBuilder =
      this.builderMethod((protocol: Protocol) => this._protocol = Some(protocol))(protocol)

    override def storeWith(hashFunction: HashFunction): PolicyBuilder =
      this.builderMethod((hashFunction: HashFunction) => this._hashFunction = Some(hashFunction))(hashFunction)

    override def storeWith(hashFunctionSalted: (HashFunction, SaltPolicy)): PolicyBuilder =
      this.builderMethod((hashFunctionSalted: (HashFunction, SaltPolicy)) => {
        this._hashFunction = Some(hashFunctionSalted._1)
        this._saltPolicy = Some(hashFunctionSalted._2)
      })(hashFunctionSalted)

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
