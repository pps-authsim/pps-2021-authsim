package it.unibo.authsim.library.dsl.attack.builders

import org.scalatest.wordspec.AnyWordSpec

import java.util.stream.Collectors

class StringBuilderTest extends AnyWordSpec:
  val alphabet = List("a", "b")
  val concurrentStringProvider = new ConcurrentStringCombinator(alphabet, 2)

  "A string provider" when {
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

