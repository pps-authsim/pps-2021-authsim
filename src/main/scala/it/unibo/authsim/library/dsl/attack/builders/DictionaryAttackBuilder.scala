package it.unibo.authsim.library.dsl.attack.builders

class DictionaryAttackBuilder extends BruteForceAttackBuilder:

  def withDictionary(dictionary: List[String]): DictionaryAttackBuilder = super.usingAlphabet(dictionary)

  def maximumCombinedWords(max: Int): DictionaryAttackBuilder = super.maximumLength(max)

  def getDictionary: List[String] = super.getAlphabet()
  def getMaximumCombinationLength: Int = super.getMaximumLength