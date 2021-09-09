package it.unibo.authsim.library.dsl.policy.checker

import it.unibo.authsim.library.dsl.policy.builders.StringPolicy.*
import it.unibo.authsim.library.dsl.policy.checker.StringPolicyChecker
import org.scalatest.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

class StringPolicyCheckerTests extends AnyWordSpec with BeforeAndAfter:

  private val EMPTY_PASSWORD = ""
  private val PASSWORD: String = "mariorossia"
  private val USERID: String = "MarioRossi"
  private val USERID_LOWERCASE: String = PASSWORD
  private val PASSWORD1: String = USERID
  private val PASSWORD_SYMBOLS: String = "Password$11"
  private val OTP = "12121212121"
  private val WRONG_OTP = "aaaaaa12"

  private val trueForValidString = (valid: String) => s"return true for the following string: ${valid} "
  private val not = afterWord("not")

  private var password = PasswordPolicy()
  private var userID = UserIDPolicy()
  private var otp = OTPPolicy()

  before {
    password = PasswordPolicy()
    userID = UserIDPolicy()
    otp = OTPPolicy()
  }

  "A StringPolicyChecker " when {
    " a policy is set " which {
      "it owns a minimum length of 5 characters and a maximum length of 20 characters" should {
        trueForValidString(PASSWORD) in {
          assert(StringPolicyChecker(password minimumLength 5 maximumLength 20) check PASSWORD)
        }
      }

      "it owns least of one symbols and number" should {
        trueForValidString(PASSWORD_SYMBOLS) in {
          assert(StringPolicyChecker(password minimumSymbols 1 minimumNumbers 1) check PASSWORD_SYMBOLS)
        }
      }

      "it owns least of two uppercase characters" should {
        trueForValidString(USERID) in {
          assert(StringPolicyChecker(userID minimumUpperChars 2) check USERID)
        }
      }

      "it owns least of four lowercase characters" should {
        trueForValidString(USERID_LOWERCASE) in {
          assert(StringPolicyChecker(userID minimumLowerChars 4) check USERID_LOWERCASE)
        }
      }

      "it is a OTPPolicy (i.e. only numbers)" should {
        trueForValidString(OTP) in {
          assert((StringPolicyChecker(otp) check OTP))
        }
      }


      "it owns a minimum length of 1 characters" should not{
        trueForValidString(EMPTY_PASSWORD) in {
          assert(!(StringPolicyChecker(password) check EMPTY_PASSWORD))
        }
      }

      "it owns least of three symbols and number" should not {
        trueForValidString(PASSWORD) in {
          assert(!(StringPolicyChecker(password minimumSymbols 3) check PASSWORD))
        }
      }

      "it owns least of three uppercase characters" should not {
        trueForValidString(PASSWORD1) in {
          assert(!(StringPolicyChecker(password minimumUpperChars 3) check PASSWORD1))
        }
      }

      "it owns a maximum length of 8 characters and it is a OTPPolicy (i.e. only numbers)" should not {
        trueForValidString(OTP) in {
          assert(!(StringPolicyChecker(otp maximumLength 8) check OTP))
        }
        trueForValidString(WRONG_OTP) in {
          assert(!(StringPolicyChecker(otp maximumLength 8) check WRONG_OTP))
        }
      }
    }
  }

