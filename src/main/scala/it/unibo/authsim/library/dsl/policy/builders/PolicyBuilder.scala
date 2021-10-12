package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.Protocol
import it.unibo.authsim.library.dsl.builder.Builder
import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

import scala.reflect.ClassTag

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
  def apply(): PolicyBuilder = BasicPolicyBuilder();
  def apply(policyName: String): PolicyBuilder = BasicPolicyBuilder(Some(policyName));

  case class BasicPolicyBuilder(private val name: Option[String] = None) extends PolicyBuilder:
    private var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty
    private var _protocol: Option[Protocol] = Option.empty
    private var _cryptographicAlgorithm: Option[CryptographicAlgorithm] = Option.empty
    private var _saltPolicy: Option[SaltPolicy] = Option.empty
    
    protected def init(policy: Policy): Unit =
      policy.credentialPolicies foreach { this.of(_) }
      if policy.transmissionProtocol.isDefined then this.transmitWith(policy.transmissionProtocol.get)
      if policy.cryptographicAlgorithm.isDefined && policy.saltPolicy.isDefined then
        this.storeWith((policy.cryptographicAlgorithm.get, policy.saltPolicy.get))
      else if policy.cryptographicAlgorithm.isDefined then
        this.storeWith(policy.cryptographicAlgorithm.get)

    override def of(credentialPolicy: (UserIDPolicy, PasswordPolicy)) = this of credentialPolicy._1 and credentialPolicy._2

    override def of(credentialPolicy: CredentialPolicy) = this.builderMethod((credentialPolicy: CredentialPolicy) =>
      def findIndexOfInstance[T: ClassTag](credentialPolicy: CredentialPolicy): Int =
        this._credentialPolicies.indexWhere {
          case _: T =>
            credentialPolicy match
              case _: T => true
              case _ => false
          case _ => false
        }

      val index: Option[Int] = List(findIndexOfInstance[UserIDPolicy](credentialPolicy),
                                    findIndexOfInstance[PasswordPolicy](credentialPolicy),
                                    findIndexOfInstance[OTPPolicy](credentialPolicy)).find(_ != -1)

      if index.isEmpty then this._credentialPolicies = this._credentialPolicies :+ credentialPolicy
      else this._credentialPolicies = this._credentialPolicies.updated(index.get, credentialPolicy)

    )(credentialPolicy)

    override def and(credentialPolicy: CredentialPolicy) = this of credentialPolicy

    override def transmitWith(protocol: Protocol) = this.builderMethod((protocol: Protocol) => this._protocol = Some(protocol))(protocol)

    override def storeWith(cryptographicAlgorithm: CryptographicAlgorithm) = this.builderMethod((cryptographicAlgorithm: CryptographicAlgorithm) => this._cryptographicAlgorithm = Some(cryptographicAlgorithm))(cryptographicAlgorithm)

    override def storeWith(cryptographicAlgorithmSalted: (CryptographicAlgorithm, SaltPolicy)) =
      this.builderMethod((cryptographicAlgorithmSalted: (CryptographicAlgorithm, SaltPolicy)) => {
        this._cryptographicAlgorithm = Some(cryptographicAlgorithmSalted._1)
        this._saltPolicy = Some(cryptographicAlgorithmSalted._2)
      })(cryptographicAlgorithmSalted)

    override def build: Policy = new Policy:

      override def name: String = BasicPolicyBuilder.this.name match
        case Some(name) => name
        case _ => "Policy"

      override def credentialPolicies: Seq[CredentialPolicy] = BasicPolicyBuilder.this._credentialPolicies

      override def cryptographicAlgorithm: Option[CryptographicAlgorithm] = BasicPolicyBuilder.this._cryptographicAlgorithm

      override def saltPolicy: Option[SaltPolicy] = BasicPolicyBuilder.this._saltPolicy

      override def transmissionProtocol: Option[Protocol] = BasicPolicyBuilder.this._protocol

      override def toString: String =
        s"${if BasicPolicyBuilder.this.name.isDefined then BasicPolicyBuilder.this.name.get else ""}Policy { " +
          s"Protocol = $transmissionProtocol, " +
          s"CryptographicAlgorithm = $cryptographicAlgorithm, SaltPolicy = $saltPolicy, " +
          s"CredentialPolicies = $credentialPolicies }"
