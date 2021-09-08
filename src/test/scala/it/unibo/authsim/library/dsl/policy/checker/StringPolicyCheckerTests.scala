package it.unibo.authsim.library.dsl.policy.checker

import it.unibo.authsim.library.dsl.policy.builders.StringPolicy.*
import it.unibo.authsim.library.dsl.policy.checker.StringPolicyChecker
import org.scalatest.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

class StringPolicyCheckerTests extends AnyWordSpec:

  private val EMPTY_PASSWORD = ""
  private val PASSWORD: String = "mariorossia"
  private val USERID: String = "MarioRossi"
  private val PASSWORD1: String = USERID
  private val PASSWORD_SYMBOLS: String = "Password$11"
  private val OTP = "12121212121"

  private val password: CredentialPolicy = PasswordPolicy()
  private val userID: CredentialPolicy = UserIDPolicy()
  private val otp: CredentialPolicy = OTPPolicy()

  "A StringPolicyChecker " when {
    " a policy is set " should {
      "return true for valid string" in {
        assert(StringPolicyChecker(password minimumLength 5 maximumLength 20) check PASSWORD)
        assert(StringPolicyChecker(password minimumSymbols 1 minimumNumbers 1) check PASSWORD_SYMBOLS)
        assert(StringPolicyChecker(userID minimumUpperChars 2) check USERID)
        assert(StringPolicyChecker(userID minimumLowerChars 4) check USERID)
      }

      "return false for invalid string" in {
        assert(!(StringPolicyChecker(password) check EMPTY_PASSWORD))
        assert(!(StringPolicyChecker(password minimumUpperChars 3) check PASSWORD1))
        assert(!(StringPolicyChecker(otp minimumNumbers 4 maximumLength 8) check OTP))
        assert(!(StringPolicyChecker(password minimumSymbols 3) check PASSWORD))
      }
    }
  }

