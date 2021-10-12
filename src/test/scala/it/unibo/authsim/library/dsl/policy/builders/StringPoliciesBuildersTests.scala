package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*
import org.scalatest.*
import org.scalatest.wordspec.AnyWordSpec

import scala.language.postfixOps

class StringPoliciesBuildersTests extends AnyWordSpec:

  private val MINIMUM_LENGTH: Int = 1
  private val SETTED_MINIMUM_SYMBOLS: Int = 4
  private val ACTUAL_MAX_LENGTH: Int = 5

  private var passwordPolicy: PasswordPolicy = null
  private var userIDPolicy: UserIDPolicy = null

  "A Policy Builder" when {
    "set nothing" should{
      passwordPolicy = PasswordPolicyBuilder() build;
      "have minimum length equivalent to 1" in {
        assert(passwordPolicy.minimumLength == MINIMUM_LENGTH)
      }
      "have no maximum length"  in {
        assert(passwordPolicy.maximumLength.isEmpty)
      }
    }

    "set maximum length" should {
      "be greater than minimum length" in {
        assertThrows[IllegalArgumentException] {
          PasswordPolicyBuilder() minimumLength 10 maximumLength 5
        }
      }
    }

    "set minimum length" should {
      "be less than maximum length" in {
        assertThrows[IllegalArgumentException] {
          UserIDPolicyBuilder() maximumLength 7 minimumLength 14
        }
      }
    }

    "set at least 4 symbols" should {
      passwordPolicy = PasswordPolicyBuilder() minimumSymbols 4 build;
      "return 4" in {
        assert(passwordPolicy.minimumSymbols.get === SETTED_MINIMUM_SYMBOLS)
      }
    }

    "call n times the same methods" should {
      "set the last call" in {
        passwordPolicy =  PasswordPolicyBuilder() maximumLength 10 maximumLength 13 maximumLength 5 build;
        assert(passwordPolicy.maximumLength.get == ACTUAL_MAX_LENGTH)
      }
    }

    "set a methods with negative number" should {
      "throws IllegalArgument Exception " in {
        assertThrows[IllegalArgumentException] {
          OTPPolicyBuilder() maximumLength -3
        }
      }
    }

    "set minium symbols 30 and maximum length 50" should {
      userIDPolicy = UserIDPolicyBuilder() minimumLength 30  maximumLength 50 build;
      "have minimum length equivalent to 30" in {
        assert(userIDPolicy.minimumLength == 30)
      }
      "have maximum length equivalent to 50" in {
        assert(userIDPolicy.maximumLength.get == 50)
      }
    }

    "set minium symbols 4, minimum uppercase 5, minimum numbers 4 and maximum length 9" should {
      "throws IllegalArgument Exception " in {
        assertThrows[IllegalArgumentException] {
          PasswordPolicyBuilder() maximumLength 9 minimumSymbols 4 minimumUpperChars 5 minimumNumbers 4
        }
      }
    }

    "set minimum length 5, maximum length 20, minium symbols 10, minimum uppercase 10, minimum numbers 10" should {
      "throws IllegalArgument Exception " in {
        assertThrows[IllegalArgumentException] {
          PasswordPolicyBuilder() minimumSymbols 5 minimumSymbols 10 minimumUpperChars 10 minimumNumbers 10 maximumLength 9
        }
      }
    }
  }

