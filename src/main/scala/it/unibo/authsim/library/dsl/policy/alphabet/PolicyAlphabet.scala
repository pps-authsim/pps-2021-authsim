package it.unibo.authsim.library.dsl.policy.alphabet

import it.unibo.authsim.library.dsl.alphabet.SymbolicAlphabet
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet

import scala.collection.immutable.ListSet
import scala.util.matching.Regex

/**
 * A ''PolicyAlphabet'' is a trait that is used to define the alphabet of a policy
 */
trait PolicyAlphabet extends RegexAlphabet with RandomAlphabet

object PolicyAlphabet:

  /**
   * ''PolicyDefaultAlphabet'' is an implementation of a default alphabet for a policy.
   *
   * The characters are:
   *
   * - lowercase : 'a' to 'z'
   *
   * - uppercase : 'A' to 'Z'
   *
   * - digits    : '0' to '9'
   *
   * - symbols   : '!' '@' '#' '$' '%' '&#94;' '&' '*'
   */
  case class PolicyDefaultAlphabet() extends PolicyAlphabet:
    override def lowers = SymbolicAlphabet(ListSet.from(for i <- 'a' to 'z' yield i.toString))
    override def uppers = SymbolicAlphabet(ListSet.from(for i <- lowers.symbolSet yield i.toUpperCase))
    override def digits = SymbolicAlphabet(ListSet.from(for i <- '0' to '9' yield i.toString))
    override def symbols = SymbolicAlphabet(ListSet("!", "@", "#", "$", "%", "^", "&", "*"))

    override def toString: String = s"PolicyDefaultAlphabet {"+
        s" lowers = " + this.lowers.mkString +
        s", uppers = " + this.uppers.mkString +
        s", digits = " + this.digits.mkString +
        s", symbols = " + this.symbols.mkString + " }"


  /**
   * ''PolicyOTPAlphabet'' is an implementation of a default alphabet for a otp policy.
   *
   * The characters are: '0' to '9'
   */
  case class PolicyOTPAlphabet() extends PolicyAlphabet:
    override def digits: SymbolicAlphabet = PolicyDefaultAlphabet().digits
    override def toString: String = s"PolicyOTPAlphabet { digits = " + this.digits.mkString + " }"
