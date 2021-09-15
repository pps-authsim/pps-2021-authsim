package it.unibo.authsim.library.dsl.policy.model

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, SaltPolicy}
import it.unibo.authsim.library.dsl.{HashFunction, Protocol}

trait Policy:
  def name: String
  def credentialPolicies: Seq[CredentialPolicy]
  def hashFunction: Option[HashFunction]
  def saltPolicy: Option[SaltPolicy]
  def transmissionProtocol: Option[Protocol]
