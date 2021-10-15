package it.unibo.authsim.library.attack.builders

import it.unibo.authsim.library.alphabet.SymbolicAlphabet
import org.scalatest.wordspec.AnyWordSpec

import java.util.stream.Collectors

class ConcurrentStringCombinatorTest extends AnyWordSpec:
  val alphabet = Set("a", "b")
  val concurrentStringProvider = new ConcurrentStringCombinator(SymbolicAlphabet(alphabet), 2)

  "A string combinator" when {
    "the max length is > 0" should {
      "have available strings" in {
        assert(!concurrentStringProvider.getNextString().isEmpty)
      }
    }
    "all the strings are consumed" should {
      "return an empty value" in {
        while !concurrentStringProvider.getNextString().isEmpty do
          ;
        end while
        assert(concurrentStringProvider.getNextString().isEmpty)
      }
    }
  }

