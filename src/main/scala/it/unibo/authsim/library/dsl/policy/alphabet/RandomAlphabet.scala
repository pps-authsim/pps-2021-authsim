package it.unibo.authsim.library.dsl.policy.alphabet

import scala.util.Random

trait RandomAlphabet extends Alphabet:
  
  def randomLowers: LazyList[Char] = this.createLazyListFromAlphabets(this.lowers)
  def randomUppers: LazyList[Char] = this.createLazyListFromAlphabets(this.uppers)
  def randomDigits: LazyList[Char] = this.createLazyListFromAlphabets(this.digits)
  def randomSymbols: LazyList[Char] = this.createLazyListFromAlphabets(this.symbols)
  def randomAlphanumericsymbols: LazyList[Char] = randomLowers ++ randomUppers ++ randomDigits ++ randomSymbols
  private def createLazyListFromAlphabets(alphabet: Seq[Char]): LazyList[Char] =
    def next: Char =
      val chars = alphabet.mkString
      chars charAt (Random nextInt chars.length)
    LazyList continually next
