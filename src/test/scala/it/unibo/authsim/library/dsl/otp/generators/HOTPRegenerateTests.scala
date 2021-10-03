package it.unibo.authsim.library.dsl.otp.generators

import it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.SecretValue
import it.unibo.authsim.library.dsl.otp.builders.HOTPBuilder
import it.unibo.authsim.library.dsl.otp.generators.OTPRegenerateHelpers.*
import it.unibo.authsim.library.dsl.otp.model.HOTP
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.generatorLength
import org.scalatest.wordspec.AnyWordSpec

import scala.language.postfixOps

class HOTPRegenerateTests extends AnyWordSpec:
  
  private val secret1: SecretValue = ("luca--verdi", "ciao-password")
  private val hotpBuilder: HOTPBuilder = HOTPBuilder() secret secret1
  private var hotp: HOTP = null
  
  "HOTP" when {
    "small length and is resetted for more time" should {
      hotp = hotpBuilder withPolicy policySmall build;
      "generate always a different pincode" in {
        checkNewPincodes(hotp)
      }
    }
    "medium length and is resetted for more time" should {
      hotp = hotpBuilder withPolicy policyMedium build;
      "generate always a different pincode" in {
        checkNewPincodes(hotp)
      }
    }

    "large length and is resetted for more time" should {
      hotp = hotpBuilder withPolicy policyLarge build;
      "generate always a different pincode" in {
        checkNewPincodes(hotp)
      }
    }
  }
