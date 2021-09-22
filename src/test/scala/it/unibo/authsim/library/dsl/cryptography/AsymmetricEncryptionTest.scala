package it.unibo.authsim.library.dsl.cryptography

import it.unibo.authsim.library.dsl.cryptography.asymmetric.{RSA}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class AsymmetricEncryptionTest extends AnyWordSpec with Matchers {
  val rsa= RSA()
}