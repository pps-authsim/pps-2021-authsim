package it.unibo.authsim.library.dsl.otp.model

import scala.concurrent.duration.Duration

/**
 * ''TOTP'' rappresents an one time password (OTP) based on hash-based message authentication codes (HMAC) and on current time as a source of uniqueness.
 */
trait TOTP extends OTP with HOTP:
  /**
   * @return a [[Duration time]] that rappresents the expiration of one time password
   */
  def timeout: Duration
  /**
   * @return an optional long that rappresents the creation date of one time password
   */
  def createDate: Option[Long]