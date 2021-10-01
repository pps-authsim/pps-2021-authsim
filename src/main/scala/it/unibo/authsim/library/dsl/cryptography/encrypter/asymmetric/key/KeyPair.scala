package it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key

trait KeyPair:
  def publicKey: String

  def privateKey: String
