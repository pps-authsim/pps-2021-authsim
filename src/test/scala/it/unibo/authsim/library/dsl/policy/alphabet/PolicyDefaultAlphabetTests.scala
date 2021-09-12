package it.unibo.authsim.library.dsl.policy.alphabet

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.*
import org.scalatest.wordspec.AnyWordSpec

class PolicyDefaultAlphabetTests extends AnyWordSpec:

  private val policyDefaultAlphabet: PolicyAlphabet = new PolicyDefaultAlphabet
  private val NUMER_CHARS: Int = 26
  private val NUMBER_DIGIT: Int = 10
  private val CHARS_LOWER: String = "abcdefghijklmnopqrstuvwxyz"
  private val CHARS_UPPER: String = CHARS_LOWER.toUpperCase
  private val DIGIT: String = "0123456789"

  private val SYMBOLS: String = "!@#$%^&*"

  "The PolicyDefaultAlphabet" should {
    "have 26 characters lowercase" in {
      assert(policyDefaultAlphabet.lowers.length == NUMER_CHARS)
    }
    "have characters from 'a' to 'z'" in {
      assert(policyDefaultAlphabet.lowers.mkString == CHARS_LOWER)
    }
    "have 26 characters uppercase" in {
      assert(policyDefaultAlphabet.uppers.length == NUMER_CHARS)
    }
    "have characters from 'A' to 'Z'" in {
      assert(policyDefaultAlphabet.uppers.mkString == CHARS_UPPER)
    }
    "have 10 charaters numeric" in {
      assert(policyDefaultAlphabet.digits.length == NUMBER_DIGIT)
    }
    "have numbers from 0 to 9" in {
      assert(policyDefaultAlphabet.digits.mkString == DIGIT)
    }
    "have symbols " + SYMBOLS in {
      assert(policyDefaultAlphabet.symbols.mkString == SYMBOLS)
    }
  }