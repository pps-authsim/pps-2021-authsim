package it.unibo.authsim.library.dsl.otp.checkers

import it.unibo.authsim.library.dsl.otp.model.*
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.*

trait OTPChecker:
  def check(pincode: String): Boolean

object OTPChecker:

  implicit val hotpChecker: HOTP => OTPChecker = (hotp: HOTP) =>  new OTPChecker:
    override def check(pincode: String): Boolean = truncate(hotp.hashFunction, hotp.secret, pincode.length)(hmac) == pincode