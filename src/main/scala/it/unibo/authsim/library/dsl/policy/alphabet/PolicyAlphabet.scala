package it.unibo.authsim.library.dsl.policy.alphabet

import scala.util.matching.Regex

/**
 * A ''PolicyAlphabet'' is a trait that is used to define the alphabet of a policy
 */
trait PolicyAlphabet extends RegexAlphabet with RandomAlphabet

object PolicyAlphabet:

  /**
   * ''PolicyDefaultAlphabet'' is an implementation of a default alphabet for a policy
   */
  class PolicyDefaultAlphabet() extends PolicyAlphabet:
    override def lowers: Seq[Char] = for i <- 'a' to 'z' yield i
    override def uppers: Seq[Char] = for i <- lowers yield i.toUpper
    override def digits: Seq[Char] = for i <- '0' to '9' yield i
    override def symbols: Seq[Char] = Seq('!', '@', '#', '$', '%', '^', '&', '*')

    override def toString: String = s"PolicyDefaultAlphabet {"+
        s" lowers = " + this.lowers.mkString +
        s", uppers = " + this.uppers.mkString +
        s", digits = " + this.digits.mkString +
        s", symbols = " + this.symbols.mkString + " }"

