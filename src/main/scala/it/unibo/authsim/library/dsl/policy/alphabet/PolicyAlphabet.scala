package it.unibo.authsim.library.dsl.policy.alphabet

import scala.util.matching.Regex

trait PolicyAlphabet extends RegexAlphabet with RandomAlphabet

object PolicyAlphabet:

  class PolicyDefaultAlphabet() extends PolicyAlphabet:
    override def lowers: Seq[Char] = for i <- 'a' to 'z' yield i
    override def uppers: Seq[Char] = for i <- lowers yield i.toUpper
    override def digits: Seq[Char] = for i <- '0' to '9' yield i
    override def symbols: Seq[Char] = Seq('!', '@', '#', '$', '%', '^', '&', '*')

