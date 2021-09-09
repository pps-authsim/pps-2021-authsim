package it.unibo.authsim.library.dsl.attack.builders

import org.scalatest.wordspec.AnyWordSpec

import java.util.stream.Collectors

class StringBuilderTest extends AnyWordSpec {
  val alphabet = List("a", "b")
  val stringBuilder = new StringBuilder(alphabet)
  val alphabet2LengthStrings = List("aa", "ab", "ba", "bb")

  "A string builder" when {
    "generating 0-length strings" should {
      "return a list with an empty string" in {
        assert(stringBuilder.getAllStringsWithLength(0) == List(""))
      }
    }

    "generating 1-length strings" should {
      "return a list with the symbols of the alphabet" in {
        assert(stringBuilder.getAllStringsWithLength(1) == alphabet)
      }
    }

    "generating 2-length strings" should {
      "return a list with the combinations of the symbols of the alphabet" in {
        assert(stringBuilder.getAllStringsWithLength(2) == alphabet2LengthStrings)
      }
    }

    "generating strings up to 0 length" should {
      "return a list with an empty string" in {
        assert(stringBuilder.getAllStringsUpToLength(0) == List(""))
      }
    }

    "generating strings up to 1 length" should {
      "return the alphabet symbol list" in {
        assert(stringBuilder.getAllStringsUpToLength(1) == alphabet)
      }
    }

    "generating strings up to 2 length" should {
      "return the alphabet symbol list and their combination" in {
        assert(stringBuilder.getAllStringsUpToLength(2) == (alphabet ++ alphabet2LengthStrings))
      }
    }
  }

  /*
    This is the "usual" alphabet for passwords:
    val fullAlphabet = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
                       .flatMap(c => List(c, c.toUpperCase))
                       ++ List(" ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~")
    val fullStringBuilder = new StringBuilder(fullAlphabet)
    println("Alphabet length: " + fullAlphabet.length)
  */
}
