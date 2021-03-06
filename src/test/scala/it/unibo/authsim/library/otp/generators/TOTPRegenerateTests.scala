package it.unibo.authsim.library.otp.generators

import it.unibo.authsim.library.otp.builders.OTPBuilder.SecretValue
import it.unibo.authsim.library.otp.builders.TOTPBuilder
import it.unibo.authsim.library.otp.generators.OTPRegenerateHelpers.*
import it.unibo.authsim.library.otp.model.TOTP
import it.unibo.authsim.library.otp.generators.LengthGenerator.generatorLength
import org.scalatest.wordspec.AnyWordSpec

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.language.postfixOps

class TOTPRegenerateTests extends AnyWordSpec:

  private val duration: Duration = Duration(5, TimeUnit.SECONDS)
  private val secret: SecretValue = ("mario-rossi", "my-password")
  private val totpBuilder: TOTPBuilder = TOTPBuilder() timeout duration secret secret
  private var totp: TOTP = null

  "TOTP" when {
    "small length and is resetted for more time" should {
      totp = totpBuilder withPolicy policySmall build;
      "generate always a different pincode" in {
        checkNewPincodes(totp)
      }
    }
    "medium length and is resetted for more time" should {
      totp = totpBuilder withPolicy policyMedium build;
      "generate always a different pincode" in {
        checkNewPincodes(totp)
      }
    }
    "large length and is resetted for more time" should {
      totp = totpBuilder withPolicy policyLarge build;
      "generate always a different pincode" in {
        checkNewPincodes(totp)
      }
    }
  }
