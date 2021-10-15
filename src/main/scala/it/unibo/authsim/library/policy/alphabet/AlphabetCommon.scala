package it.unibo.authsim.library.policy.alphabet

import it.unibo.authsim.library.alphabet.SymbolicAlphabet

/**
 * ''AlphabetCommon'' is a trait that is used to define a new alphabet
 */
trait AlphabetCommon:
  /**
   * @return sequence of lowercase characters of alphabet
   */
  def lowers: SymbolicAlphabet = SymbolicAlphabet()
  /**
   * @return sequence of uppercase characters of alphabet
   */
  def uppers: SymbolicAlphabet = SymbolicAlphabet()
  /**
   * @return sequence of digits of alphabet
   */
  def digits: SymbolicAlphabet = SymbolicAlphabet()
  /**
   * @return sequence of symbols of alphabet
   */
  def symbols: SymbolicAlphabet = SymbolicAlphabet()
  /**
   * @return sequence of all characters (lowercase and uppercase), digits and symbols of alphabet
   */
  def alphanumericsymbols: SymbolicAlphabet = lowers and uppers and digits and symbols
