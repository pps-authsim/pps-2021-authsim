package it.unibo.authsim.library.dsl.otp

import it.unibo.authsim.library.dsl.HashFunction
import it.unibo.authsim.library.dsl.policy.checkers.PolicyChecker
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.defaults.stringpolicy.OTPPolicyDefault
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.OTPPolicy
import it.unibo.authsim.library.dsl.otp.OneTimePassword.*
import org.scalatest.wordspec.AnyWordSpec

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.language.postfixOps

class OneTimePasswordTests extends AnyWordSpec:

  private val policy: OTPPolicy = OTPPolicyDefault.SIMPLE
  private val policyChecker: PolicyChecker[String] = StringPolicyChecker(policy)

  private val duration: Duration = Duration(5, TimeUnit.SECONDS)
  private val hashFunction: HashFunction = HashFunction.SHA256()
  private val pincodeWrong: String = "12345"

  private var totp: TOTP = null
  private var tOTPGenerated: String = null

  private var hotp: HOTP = null
  private var hmacOTPGenerated: String = null

  private val not = afterWord("not")

  "A HOTP" when {
    hotp = OneTimePassword(hashFunction).secret(("lucaverdi", "my-password"))
    "is defined" should {
      s"have like hash function: ${hashFunction}" in {
        assert(hotp.hashFunction == hashFunction)
      }
    }
    "a policy is set " should {
      hotp.withPolicy(policy);
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
    totp = OneTimePassword(duration).secret(("mario-rossi", "my-password"))
    "is defined" should {
      s"have be valid for ${duration.toSeconds} seconds" in {
        assert(totp.asInstanceOf[TOTP].timeout.toSeconds == duration.toSeconds)
      }
    }
    "a policy is set " should {
      totp.withPolicy(policy);
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
