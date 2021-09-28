package it.unibo.authsim.library.dsl.policy.alphabet

import scala.util.matching.Regex

/**
 * An ''Alphabet'' is a trait that is used to define a new alphabet
 */
trait Alphabet:
  /**
   * @return sequence of lowercase characters of alphabet
   */
  def lowers: Seq[Char] = Seq.empty
  /**
   * @return sequence of uppercase characters of alphabet
   */
  def uppers: Seq[Char] = Seq.empty
  /**
   * @return sequence of digits of alphabet
   */
  def digits: Seq[Char] = Seq.empty
  /**
   * @return sequence of symbols of alphabet
   */
  def symbols: Seq[Char] = Seq.empty
  /**
   * @return sequence of all characters (lowercase and uppercase), digits and symbols of alphabet
   */
  def alphanumericsymbols: Seq[Char] = lowers ++ uppers ++ digits ++ symbols
