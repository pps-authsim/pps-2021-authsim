package it.unibo.authsim.library.dsl.policy

import scala.util.Random

object AlphabetPolicy:

  val lowerAlphabet: Seq[Char] = for i <- 'a' to 'z' yield i
  val upperAlphabet: Seq[Char] = for i <- 'A' to 'Z' yield i
  val digitAlphabet: Seq[Char] = for i <- '0' to '9' yield i
  val symbolsAlphabet: Seq[Char] = Seq('!', '@', '#', '$', '%', '^', '&', '*')
  val alphabet: Seq[Char] = lowerAlphabet ++ upperAlphabet ++ digitAlphabet ++ symbolsAlphabet

  extension (r: Random)
    private def createLazyListFromAlphabets(alphabet: Seq[Char]): LazyList[Char] =
      def next: Char =
        val chars = alphabet.mkString
        chars charAt (r nextInt chars.length)
      LazyList continually next

    def lowers: LazyList[Char] = createLazyListFromAlphabets(lowerAlphabet)
    def uppers: LazyList[Char] = createLazyListFromAlphabets(upperAlphabet)
    def digits: LazyList[Char] = createLazyListFromAlphabets(digitAlphabet)
    def symbols: LazyList[Char] = createLazyListFromAlphabets(symbolsAlphabet)
    def alphanumericsymbols: LazyList[Char] = createLazyListFromAlphabets(alphabet)
