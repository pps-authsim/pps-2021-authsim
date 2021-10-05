package it.unibo.authsim.library.dsl.otp

import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.otp.builders.*
import it.unibo.authsim.library.dsl.otp.builders.OTPBuilder.*
import it.unibo.authsim.library.dsl.otp.model.*
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.generatorLength
import it.unibo.authsim.library.dsl.policy.checkers.{PolicyChecker, StringPolicyChecker}
import it.unibo.authsim.library.dsl.policy.defaults.stringpolicy.OTPPolicyDefault
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy
import org.scalatest.wordspec.AnyWordSpec

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.language.postfixOps

class OTPTests extends AnyWordSpec:

  private val policy: OTPPolicy = OTPPolicyDefault.SIMPLE
  private val policyChecker: PolicyChecker[String] = StringPolicyChecker(policy)

  private val duration: Duration = Duration(5, TimeUnit.SECONDS)
  private val hashFunction: HashFunction = HashFunction.SHA256()
  private val pincodeWrong: String = "12345"
  private val secret1: SecretValue = ("lucaverdi", "my-password")
  private val secret2: SecretValue = ("mario-rossi", "my-password")

  private var totp: TOTP = TOTPBuilder().timeout(duration).secret(secret2).withPolicy(policy).build
  private var tOTPGenerated: String = null

  private var hotp: HOTP = HOTPBuilder().hashFunction(hashFunction).secret(secret1).withPolicy(policy).build
  private var hmacOTPGenerated: String = null

  private val not = afterWord("not")

  "A HOTP" when {
    println(hotp)
    "is defined" should {
      s"have like hash function: ${hashFunction}" in {
        assert(hotp.hashFunction == hashFunction)
      }
    }
    "a policy is set " should {
      hmacOTPGenerated = hotp.generate
      "generate a value that respects it" in {
        assert(policyChecker check hmacOTPGenerated)
      }
    }
    s"check pincode $hmacOTPGenerated" should {
      "return true" in {
        assert(hotp check hmacOTPGenerated)
      }
    }
    s"check pincode $pincodeWrong" should {
      "return false " in {
        assert(!(hotp check pincodeWrong))
      }
    }
  }


  "A TOTP " when {
    println(totp)
    "is defined" should {
      s"have be valid for ${duration.toSeconds} seconds" in {
        assert(totp.asInstanceOf[TOTP].timeout.toSeconds == duration.toSeconds)
      }
    }
    "a policy is set " should {
      tOTPGenerated = totp.generate
      "generate a value that respects it" in {
        assert(policyChecker check tOTPGenerated)
      }
    }
    s"is passed the time: ${duration.toSeconds} seconds" should not {
      "be valid the pincode generated" in {
        Thread.sleep(duration.toMillis)
        assert(!(totp check tOTPGenerated))
      }
    }
  }