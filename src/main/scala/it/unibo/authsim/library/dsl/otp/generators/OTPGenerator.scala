package it.unibo.authsim.library.dsl.otp.generators

import it.unibo.authsim.library.dsl.otp.model.*
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.*

import scala.util.Random

trait OTPGenerator:
  def generate: String

object OTPGenerator:

  implicit val hotpGenerator: HOTP => OTPGenerator = (hotp: HOTP) => new OTPGenerator:
    override def generate: String = truncate(hotp.hashFunction, hotp.secret, Random.between(hotp.policy.minimumLength, hotp.policy.maximumLength))(hmac)