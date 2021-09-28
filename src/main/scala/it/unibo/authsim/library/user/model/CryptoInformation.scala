package it.unibo.authsim.library.user.model

import it.unibo.authsim.library.dsl.policy.model.Policy

/**
 * Trait that represent a CryptoIntormation
 */
trait CryptoInformation(algorithmPolicy:Policy)
/*
NB Abbiamo deciso che viene criptata solo la password ->
per me qui ci sta di più l'algoritmo(hash, (a)symmetric estenderanno da un trait comune 'CryptographicAlgorithm') che la policy
 */

/**
 * Object that represent a CryptoIntormation
 */
object CryptoInformation:
  //TODO documentation when implementation is stable
  def apply(algorithmPolicy: Policy): CryptoInformation = CryptoInformationImpl(algorithmPolicy)
  private case class CryptoInformationImpl(algorithmPolicy: Policy) extends CryptoInformation(algorithmPolicy)