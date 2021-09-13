package it.unibo.authsim.library.dsl.policy.checkers

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*
import org.scalatest.*
import org.scalatest.wordspec.AnyWordSpec

import scala.language.postfixOps

class StringPolicyCheckerTests extends AnyWordSpec:

  private val EMPTY_PASSWORD = ""
  private val PASSWORD: String = "mariorossia"
  private val USERID: String = "MarioRossi"
  private val USERID_LOWERCASE: String = PASSWORD
  private val PASSWORD1: String = USERID
  private val PASSWORD_SYMBOLS: String = "Password$11"
  private val OTP = "12121212121"
  private val WRONG_OTP = "aaaaaa12"

  private val trueForValidString = (valid: String) => s"return true for the following string: ${valid}"
  private val not = afterWord("not")

  private var passwordPolicy: PasswordPolicy = null
  private var userIDPolicy: UserIDPolicy = null
  private var otpPolicy: OTPPolicy = null


  "A StringPolicyChecker " when {
    " a policy is set " which {
      "it owns a minimum length of 5 characters and a maximum length of 20 characters" should {
        trueForValidString(PASSWORD) in {
          passwordPolicy = PasswordPolicyBuilder() minimumLength 5 maximumLength 20 build;
          assert(StringPolicyChecker(passwordPolicy) check PASSWORD)
        }
      }

      "it owns least of one symbols and number" should {
        trueForValidString(PASSWORD_SYMBOLS) in {
          passwordPolicy = PasswordPolicyBuilder() minimumSymbols 1 minimumNumbers 1 build;
          assert(StringPolicyChecker(passwordPolicy) check PASSWORD_SYMBOLS)
        }
      }

      "it owns least of two uppercase characters" should {
        trueForValidString(USERID) in {
          userIDPolicy = UserIDPolicyBuilder() minimumUpperChars 2 build;
          assert(StringPolicyChecker(userIDPolicy) check USERID)
        }
      }

      "it owns least of four lowercase characters" should {
        trueForValidString(USERID_LOWERCASE) in {
          userIDPolicy = UserIDPolicyBuilder() minimumLowerChars 4 build;
          assert(StringPolicyChecker(userIDPolicy) check USERID_LOWERCASE)
        }
      }

      "it is a OTPPolicy (i.e. only numbers)" should {
        trueForValidString(OTP) in {
          otpPolicy = OTPPolicyBuilder() build;
          assert(StringPolicyChecker(otpPolicy) check OTP)
        }
      }

      "it owns a minimum length of 1 characters" should not{
        trueForValidString(EMPTY_PASSWORD) in {
          assert(!(StringPolicyChecker(PasswordPolicyBuilder() build) check EMPTY_PASSWORD))
        }
      }

      "it owns least of three symbols and number" should not {
        trueForValidString(PASSWORD) in {
          passwordPolicy = PasswordPolicyBuilder() minimumSymbols 3 build;
          assert(!(StringPolicyChecker(passwordPolicy) check PASSWORD))
        }
      }

      "it owns least of three uppercase characters" should not {
        trueForValidString(PASSWORD1) in {
          passwordPolicy = PasswordPolicyBuilder() minimumUpperChars 3 build;
          assert(!(StringPolicyChecker(passwordPolicy) check PASSWORD1))
        }
      }

      "it owns a maximum length of 8 characters and it is a OTPPolicy (i.e. only numbers)" should not {
        trueForValidString(OTP) in {
          otpPolicy = OTPPolicyBuilder() maximumLength 8 build;
          assert(!(StringPolicyChecker(otpPolicy) check OTP))
        }

        trueForValidString(WRONG_OTP) in {
          otpPolicy = OTPPolicyBuilder() maximumLength 8 build;
          assert(!(StringPolicyChecker(otpPolicy) check WRONG_OTP))
        }
      }
    }
  }

  "A PasswordPolicyBuilder " when {
    "called 'check' for a policy " which {

      "it owns a minimum length of 5 characters and a maximum length of 20 characters" should {
        trueForValidString(PASSWORD) in {
          assert(PasswordPolicyBuilder() minimumLength 5 maximumLength 20 check PASSWORD)
        }
      }

      "it owns least of one symbols and number" should {
        trueForValidString(PASSWORD_SYMBOLS) in {
          assert(PasswordPolicyBuilder() minimumSymbols 1 minimumNumbers 1 check PASSWORD_SYMBOLS)
        }
      }


      "it owns a minimum length of 1 characters" should not{
        trueForValidString(EMPTY_PASSWORD) in {
          assert(!(PasswordPolicyBuilder() check EMPTY_PASSWORD))
        }
      }

      "it owns least of three symbols and number" should not {
        trueForValidString(PASSWORD) in {
          assert(!(PasswordPolicyBuilder() minimumSymbols 3 check PASSWORD))
        }
      }

      "it owns least of three uppercase characters" should not {
        trueForValidString(PASSWORD1) in {
          assert(!( PasswordPolicyBuilder() minimumUpperChars 3 check PASSWORD1))
        }
      }
    }
  }

  "A UserIDPolicyBuilder " when {
    "called 'check' for a policy " which {

      "it owns least of two uppercase characters" should {
        trueForValidString(USERID) in {
          assert(UserIDPolicyBuilder() minimumUpperChars 2 check USERID)
        }
      }

      "it owns least of four lowercase characters" should {
        trueForValidString(USERID_LOWERCASE) in {
          assert(UserIDPolicyBuilder() minimumLowerChars 4 check USERID_LOWERCASE)
        }
      }
    }
  }

  "A OTPPolicyBuilder " when {
    "called 'check' for a policy " which {

      "it is a OTPPolicy (i.e. only numbers)" should {
        trueForValidString(OTP) in {
          assert( OTPPolicyBuilder()check OTP)
        }
      }

      "it owns a maximum length of 8 characters and it is a OTPPolicy (i.e. only numbers)" should not {
        trueForValidString(OTP) in {
          assert(!(OTPPolicyBuilder() maximumLength 8 check OTP))
        }
        trueForValidString(WRONG_OTP) in {
          assert(!(OTPPolicyBuilder() maximumLength 8 check WRONG_OTP))
        }
      }
    }
  }