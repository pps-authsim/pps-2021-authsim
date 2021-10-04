package it.unibo.authsim.library.dsl.alphabet

import org.scalatest.wordspec.AnyWordSpec

class AlphabetTest extends AnyWordSpec {
  val symbolSet = Set("a", "b", "c", "d")
  val anotherSymbolSet = Set("a", "d", "e")
  val symbolsWithSpace = Set("a", "b", " ", "d")
  val symbolsWithHigherLength = Set("aa", "b", "cc")
  val dictionarySet = Set("password", "hunter", "hello")
  val dictionaryWithSpace = Set("password", " ", "hunter", "hello")

  "A symbolic alphabet" when {
    "the symbolic set has a space character" should {
      "give an error" in {
        assertThrows[IllegalArgumentException](SymbolicAlphabet(symbolsWithSpace))
      }
    }
    "the set has no space characters" should {
      "return a new alphabet" in {
        assert(SymbolicAlphabet(symbolSet).isInstanceOf[SymbolicAlphabet])
      }
    }
    "the set has a lengthy symbol" should {
      "give an error" in {
        assertThrows[IllegalArgumentException](SymbolicAlphabet(symbolsWithHigherLength))
      }
    }
    "adding itself" should {
      val symbolicAlphabet = SymbolicAlphabet(symbolSet)
      "contain the same symbols as before" in {
        assert((symbolicAlphabet and symbolicAlphabet) equals symbolicAlphabet)
      }
    }
    "adding a dictionary" should {
      val symbolicAlphabet = SymbolicAlphabet(symbolSet)
      val dictionaryAlphabet = Dictionary(dictionarySet)
      "throw an error" in {
        assertThrows[IllegalArgumentException](symbolicAlphabet and dictionaryAlphabet)
      }
    }
    "adding another SymbolicAlphabet" should {
      val alphabet1 = SymbolicAlphabet(symbolSet)
      val alphabet2 = SymbolicAlphabet(anotherSymbolSet)
      "return a new symbolic alphabet with all the symbols" in {
        assert((alphabet1 and alphabet2).symbolSet equals (alphabet1.symbolSet concat alphabet2.symbolSet))
      }
    }
  }

  "A dictionary" when {
    "the word set has a blank word" should {
      "give an error" in {
        assertThrows[IllegalArgumentException](Dictionary(dictionaryWithSpace))
      }
    }
    "the word set has no blank words" should {
      "return a new dictionary" in {
        assert(Dictionary(dictionarySet).isInstanceOf[Dictionary])
      }
    }
    "adding itself" should {
      val dictionaryAlphabet = Dictionary(dictionarySet)
      "contain the same symbols as before" in {
        assert((dictionaryAlphabet and dictionaryAlphabet) equals dictionaryAlphabet)
      }
    }
    "adding a symbolic alphabet" should {
      val dictionaryAlphabet = Dictionary(dictionarySet)
      val symbolicAlphabet = SymbolicAlphabet(symbolSet)
      "contain the symbols of both" in {
        assert((dictionaryAlphabet and symbolicAlphabet).symbolSet equals (dictionaryAlphabet.symbolSet concat symbolicAlphabet.symbolSet))
      }
    }
  }
}
