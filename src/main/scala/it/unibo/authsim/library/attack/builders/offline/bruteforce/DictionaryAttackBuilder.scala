package it.unibo.authsim.library.attack.builders.offline.bruteforce

import it.unibo.authsim.library.attack.builders.offline.bruteforce.AbstractBruteForceAttackBuilder
import it.unibo.authsim.library.alphabet.{Alphabet, Dictionary}

/**
 * Builder of dictionary attacks.
 * They can be seen as brute force attacks wht a special alphabet with symbols with a length greater than 1.
 */
class DictionaryAttackBuilder extends AbstractBruteForceAttackBuilder[Dictionary]:

  def withDictionary(dictionary: Alphabet[Dictionary]) = super.protectedUsingAlphabet(dictionary)

  def maximumCombinedWords(max: Int) = super.protectedMaximumLength(max)

  def getDictionary = super.protectedGetAlphabet

  def getMaximumCombinationLength: Int = super.protectedGetMaximumLength