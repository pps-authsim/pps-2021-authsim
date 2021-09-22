package it.unibo.authsim.library.user.model

import it.unibo.authsim.library.dsl.policy.model.Policy

trait CryptoInformation(algorithmPolicy:Policy)
/*
NB Abbiamo deciso che viene criptata solo la password ->
per me qui ci sta di più l'algoritmo(hash, (a)symmetric estenderanno da un trait comune 'CryptographicAlgorithm') che la policy
 */
object CryptoInformation:
  def apply(algorithmPolicy: Policy): CryptoInformation = CryptoInformationImpl(algorithmPolicy)
//non sono sicura abbia senso tenere una case class per sta roba
  case class CryptoInformationImpl(algorithmPolicy: Policy) extends CryptoInformation(algorithmPolicy)