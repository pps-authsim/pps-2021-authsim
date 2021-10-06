package it.unibo.authsim.library.dsl.policy.model

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, SaltPolicy}
import it.unibo.authsim.library.dsl.Protocol

import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
 * A ''Policy'' is a trait that is used to define a new policy
 */
trait Policy:
  /**
   * @return name of the policy
   */
  def name: String
  /**
   * @return sequence of [[CredentialPolicy credential policies]]
   */
  def credentialPolicies: Seq[CredentialPolicy]
  /**
   * @return an optional [[CryptographicAlgorithm cryptographic algorithm]] used to store the credentials on database
   */
  def cryptographicAlgorithm: Option[CryptographicAlgorithm]
  /**
   * @return an optional [[SaltPolicy salt policy]] used to check an eventual salt value to encrypt credential
   */
  def saltPolicy: Option[SaltPolicy]
  /**
   * @return an optional [[Protocol protocol]] of transmission of the credentials on network
   */
  def transmissionProtocol: Option[Protocol]
