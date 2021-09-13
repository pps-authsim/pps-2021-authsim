package it.unibo.authsim.library.dsl.policy.alphabet

import org.scalatest.wordspec.AnyWordSpec

class AlphabetsTests extends AnyWordSpec:

  private val alphabet: Alphabet = new Alphabet:
    override def lowers: Seq[Char] = for i <- 'a' to 't' yield i
    override def uppers: Seq[Char] = for i <- 'A' to 'T' yield i
    override def symbols: Seq[Char] = Seq('$', '%')

  private val lower: Seq[Char] = for i <- 'u' to 'z' yield i
  private val upper: Seq[Char] = for j <- lower yield j.toUpper

  private val not = afterWord("not")

  "The alphabet " should not {
    "have numeric characters" in {
      assert(alphabet.digits.isEmpty)
    }

    "have symbols except '$' and '%'" in{
      assert(alphabet.symbols.length == 2 && alphabet.symbols.contains('$') && alphabet.symbols.contains('%'))
    }

    "have lowercase characters in the sequence " + lower  in{
      assert(!alphabet.lowers.containsSlice(lower))
    }

    "have uppercase characters in the sequence " + upper  in{
      assert(!alphabet.lowers.containsSlice(upper))
    }
  }

