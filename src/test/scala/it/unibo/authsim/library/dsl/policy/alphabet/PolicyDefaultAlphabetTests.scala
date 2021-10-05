package it.unibo.authsim.library.dsl.policy.alphabet

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
      assert(policyDefaultAlphabet.lowers.size == NUMER_CHARS)
    }
    "have characters from 'a' to 'z'" in {
      assert(CHARS_LOWER.toCharArray.map(_.toString).forall(policyDefaultAlphabet.lowers.contains))
    }
    "have 26 characters uppercase" in {
      assert(policyDefaultAlphabet.uppers.size == NUMER_CHARS)
    }
    "have characters from 'A' to 'Z'" in {
      assert(CHARS_UPPER.toCharArray.map(_.toString).forall(policyDefaultAlphabet.uppers.contains))
    }
    "have 10 charaters numeric" in {
      assert(policyDefaultAlphabet.digits.size == NUMBER_DIGIT)
    }
    "have numbers from 0 to 9" in {
      assert(DIGIT.toCharArray.map(_.toString).forall(policyDefaultAlphabet.digits.contains))
    }
    "have symbols " + SYMBOLS in {
      assert(SYMBOLS.toCharArray.map(_.toString).forall(policyDefaultAlphabet.symbols.contains))
    }
  }