package it.unibo.authsim.library.policy.alphabet

import it.unibo.authsim.library.alphabet.SymbolicAlphabet
import org.scalatest.wordspec.AnyWordSpec

class AlphabetCommonTests extends AnyWordSpec:

  private val alphabet: AlphabetCommon = new AlphabetCommon:
    override def lowers = SymbolicAlphabet(Set.from(for i <- 'a' to 't' yield i.toString))
    override def uppers = SymbolicAlphabet(Set.from(for i <- 'A' to 'T' yield i.toString))
    override def symbols = SymbolicAlphabet(Set("$", "%"))

  private val lower = SymbolicAlphabet(Set.from(for i <- 'u' to 'z' yield i.toString))
  private val upper = SymbolicAlphabet(Set.from(for j <- lower yield j.toUpperCase))

  private val not = afterWord("not")

  "The alphabet " should not {
    "have numeric characters" in {
      assert(alphabet.digits.isEmpty)
    }

    "have symbols except '$' and '%'" in{
      assert(alphabet.symbols.size == 2 && alphabet.symbols.contains("$") && alphabet.symbols.contains("%"))
    }

    "have lowercase characters in the sequence " + lower  in{
      assert(!lower.forall(alphabet.lowers.contains))
    }

    "have uppercase characters in the sequence " + upper  in{
      assert(!upper.forall(alphabet.lowers.contains))
    }
  }

