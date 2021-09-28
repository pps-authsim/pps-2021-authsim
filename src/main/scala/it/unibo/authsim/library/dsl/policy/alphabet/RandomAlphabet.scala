package it.unibo.authsim.library.dsl.policy.alphabet

import scala.util.Random

/**
 * A ''RandomAlphabet'' a trait that is used to define a new alphabet with randomize methods
 */
trait RandomAlphabet extends Alphabet:
  /**
   * @return stream of lowercase characters of alphabet
   */
  def randomLowers: LazyList[Char] = this.createLazyListFromAlphabets(this.lowers)
  /**
   * @return stream of uppercase characters of alphabet
   */
  def randomUppers: LazyList[Char] = this.createLazyListFromAlphabets(this.uppers)
  /**
   * @return stream of digits of alphabet
   */
  def randomDigits: LazyList[Char] = this.createLazyListFromAlphabets(this.digits)
  /**
   * @return stream of symbols of alphabet
   */
  def randomSymbols: LazyList[Char] = this.createLazyListFromAlphabets(this.symbols)
  /**
   * @return stream of all characters (lowercase and uppercase), digits and symbols of alphabet
   */
  def randomAlphanumericsymbols: LazyList[Char] = this.createLazyListFromAlphabets(this.lowers ++ this.uppers ++ this.digits ++ this.symbols)

  /**
   * @param alphabet sequence of characters of the alphabet
   * @return stream of chracters of the given alphabet
   */
  private def createLazyListFromAlphabets(alphabet: Seq[Char]): LazyList[Char] =
    def next: Char =
      val chars = alphabet.mkString
      chars charAt (Random nextInt chars.length)
    LazyList continually next
