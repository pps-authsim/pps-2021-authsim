package it.unibo.authsim.library.user.model

import it.unibo.authsim.library.dsl.policy.model.Policy

/**
 * Trait that represent a CryptoInformation
 */
trait CryptoInformation(algorithmPolicy:Policy)

/**
 * Object that represent a CryptoIntormation
 */
object CryptoInformation:
  //TODO documentation when implementation is stable
  def apply(algorithmPolicy: Policy): CryptoInformation = CryptoInformationImpl(algorithmPolicy)
  private case class CryptoInformationImpl(algorithmPolicy: Policy) extends CryptoInformation(algorithmPolicy)