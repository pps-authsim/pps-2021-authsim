package it.unibo.authsim.library.dsl.attack.builders

/**
 * Builder of dictionary attacks.
 * They can be seen as brute force attacks wht a special alphabet with symbols with a length greater than 1.
 */
class DictionaryAttackBuilder extends BruteForceAttackBuilder:

  /**
   * Sets the dictionary for the attack.
   * @param dictionary The dictionary to use.
   * @return The builder.
   */
  def withDictionary(dictionary: List[String]): DictionaryAttackBuilder = super.usingAlphabet(dictionary)

  /**
   * Sets the maximum number of words that can be combined to form the password to test.
   * @param max The masimum number.
   * @return The builder.
   */
  def maximumCombinedWords(max: Int): DictionaryAttackBuilder = super.maximumLength(max)

  /**
   * @return The set dictionary.
   */
  def getDictionary: List[String] = super.getAlphabet()

  /**
   * @return The set maximum number of combination.
   */
  def getMaximumCombinationLength: Int = super.getMaximumLength