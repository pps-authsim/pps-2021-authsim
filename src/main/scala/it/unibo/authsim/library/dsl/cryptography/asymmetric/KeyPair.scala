package it.unibo.authsim.library.dsl.cryptography.asymmetric

trait KeyPair:
  def publicKey: String
  def privateKey: String