package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.policy.builders.StringPolicy.*
import org.scalatest.*
import org.scalatest.wordspec.AnyWordSpec

class StringPolicyTests extends AnyWordSpec with BeforeAndAfter:

  private val MINIMUM_LENGTH: Int = 1
  private val SETTED_MINIMUM_SYMBOLS: Int = 4
  private val ACTUAL_MAX_LENGTH: Int = 5

  private var password = PasswordPolicy()
  private var userID = UserIDPolicy()
  private var otp = OTPPolicy()

  before {
    password = PasswordPolicy()
    userID = UserIDPolicy()
    otp = OTPPolicy()
  }

  "A Policy Builder" when {
    "set nothing" should{
      "have minimum length equivalent to 1" in {
        assert(password.getMinimumLength == MINIMUM_LENGTH)
      }
      "have maximum length equivalent to " + Int.MaxValue  in {
        assert(password.getMaximumLength == Int.MaxValue)
      }
    }

    "set maximum length" should {
      "be greater than minimum length" in {
        assertThrows[IllegalArgumentException] {
          password minimumLength 10 maximumLength 5
        }
      }
    }

    "set minimum length" should {
      "be less than maximum length" in {
        assertThrows[IllegalArgumentException] {
          userID maximumLength 7 minimumLength 14
        }
      }
    }

    "set at least 4 symbols" should {
      "return 4" in {
        password minimumSymbols 4
        assert(password.getMinimumSymbols === SETTED_MINIMUM_SYMBOLS)
      }
    }

    "call n times the same methods" should {
      "set the last call" in {
        password maximumLength 10 maximumLength 13 maximumLength 5
        assert(password.getMaximumLength == ACTUAL_MAX_LENGTH)
      }
    }

    "set a methods with negative number" should {
      "throws IllegalArgument Exception " in {
        assertThrows[IllegalArgumentException] {
          otp maximumLength -3
        }
      }
    }
  }

