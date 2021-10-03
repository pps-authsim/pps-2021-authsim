package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*
import org.scalatest.*
import org.scalatest.wordspec.AnyWordSpec

import scala.language.postfixOps

class StringPoliciesBuildersTests extends AnyWordSpec:

  private val MINIMUM_LENGTH: Int = 1
  private val MAXIMUM_LENGTH: Int = 20
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
      "have maximum length equivalent to 20"  in {
        assert(passwordPolicy.maximumLength == MAXIMUM_LENGTH)
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
        assert(passwordPolicy.minimumSymbols === SETTED_MINIMUM_SYMBOLS)
      }
    }

    "call n times the same methods" should {
      "set the last call" in {
        passwordPolicy =  PasswordPolicyBuilder() maximumLength 10 maximumLength 13 maximumLength 5 build;
        assert(passwordPolicy.maximumLength == ACTUAL_MAX_LENGTH)
      }
    }

    "set a methods with negative number" should {
      "throws IllegalArgument Exception " in {
        assertThrows[IllegalArgumentException] {
          OTPPolicyBuilder() maximumLength -3
        }
      }
    }
  }

