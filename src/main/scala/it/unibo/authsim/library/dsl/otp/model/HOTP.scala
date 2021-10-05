package it.unibo.authsim.library.dsl.otp.model

import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction

/**
 * ''HOTP'' rappresents an one time password (OTP) based on hash-based message authentication codes (HMAC).
 */
trait HOTP extends OTP:
  /**
   * @return a hash function used to generate the one time password
   */
  def hashFunction: HashFunction