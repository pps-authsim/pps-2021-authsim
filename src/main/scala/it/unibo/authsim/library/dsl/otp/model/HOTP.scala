package it.unibo.authsim.library.dsl.otp.model

import it.unibo.authsim.library.dsl.HashFunction

trait HOTP extends OTP:
  def hashFunction: HashFunction