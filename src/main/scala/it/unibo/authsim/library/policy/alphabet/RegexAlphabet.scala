package it.unibo.authsim.library.policy.alphabet

import scala.util.matching.Regex

/**
 * A ''RegexAlphabet'' is a trait that is used to define a new alphabet with some basic methods for comparing any strings
 */
trait RegexAlphabet extends AlphabetCommon:
  /**
   * Used to check that a string has at least 1 alphanumeric character
   * @return regular expression
   */
  def minimalLength: Regex = ("(?:[" + this.alphanumericsymbols.mkString + "])+").r
  /**
   * Used to check that a string has at least  ''number''  alphanumeric characters
   * @param number minimum number of characters a string must have.
   * @return regular expression
   * @throws PatternSyntaxException whether ''number'' is less than 0
   */
  def minimumLength(number: Int): Regex = ("(?:[" + this.alphanumericsymbols.mkString + "]{" + number + ",})").r
  /**
   * Used to check that a string has length betweek ''min''  and  ''max''
   * @param min minimum number of characters a string must have.
   * @param max maximum number of characters a string must have.
   * @return regular expression
   * @throws PatternSyntaxException whether ''min'' or ''max'' is less than 0 and whether ''max'' is less than ''min''
   */
  def rangeLength(min: Int, max: Int): Regex = ("(?:^[" + this.alphanumericsymbols.mkString + "]{" + min + "," + max + "}$)").r
  /**
   * Used to check that a string has at least  ''number'' lowercase characters
   * @param number minimum number of lowercase characters a string must have.
   * @return regular expression
   * @throws PatternSyntaxException whether ''number'' is less than 0
   */
  def minimumLowerCharacters(number: Int): Regex = ("(?:.*[" + this.lowers.mkString + "]){" + number + ",}.*").r
  /**
   * Used to check that a string has at least  ''number'' uppercase characters
   * @param number minimum number of uppercase characters a string must have.
   * @return regular expression
   * @throws PatternSyntaxException whether ''number'' is less than 0
   */
  def minimumUpperCharacters(number: Int): Regex = ("(?:.*[" + this.uppers.mkString + "]){" + number + ",}.*").r
  /**
   * Used to check that a string has at least  ''number'' symbols
   * @param number minimum number of symbols a string must have.
   * @return regular expression
   * @throws PatternSyntaxException whether ''number'' is less than 0
   */
  def minimumSymbols(number: Int): Regex = ("(?:.*[" + this.symbols.mkString + "]){" + number + ",}.*").r
  /**
   * Used to check that a string has at least  ''number'' digits
   * @param number minimum number of digits a string must have.
   * @return regular expression
   * @throws PatternSyntaxException whether ''number'' is less than 0
   */
  def minimumNumbers(number: Int): Regex = ("(?:.*[" + this.digits.mkString + "]){" + number + ",}.*").r
  /**
   * Used to check that a string is a numeric string
   * @return regular expression
   */
  def onlyNumbers: Regex = ("(?:^[" + this.digits.mkString + "]*$)").r
