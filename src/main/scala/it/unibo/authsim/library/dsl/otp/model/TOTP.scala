package it.unibo.authsim.library.dsl.otp.model

import scala.concurrent.duration.Duration

trait TOTP extends OTP with HOTP:
  def timeout: Duration
  def createDate: Option[Long]