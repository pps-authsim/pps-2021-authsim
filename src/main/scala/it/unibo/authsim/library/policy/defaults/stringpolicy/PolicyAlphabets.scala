package it.unibo.authsim.library.policy.defaults.stringpolicy

import it.unibo.authsim.library.alphabet.SymbolicAlphabet
import it.unibo.authsim.library.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet

import scala.collection.immutable.ListSet

private[stringpolicy] object PolicyAlphabets:

  private val policyAlphabet = PolicyDefaultAlphabet()

  /**
   * @return a policy alphabet with only lowercase characters : 'a' to 'z'
   */
  def superSimpleAlphabet: PolicyAlphabet = new PolicyAlphabet:
    override def lowers: SymbolicAlphabet = policyAlphabet.lowers

    override def toString: String = s"PolicyAlphabet { lowers = ${this.lowers.mkString} }"

  /**
   * @return a policy alphabet with lowercase and uppercase characters : 'a' to 'z' , 'A' to 'Z'
   */
  def simpleAlphabet: PolicyAlphabet = new PolicyAlphabet:
    override def lowers: SymbolicAlphabet = policyAlphabet.lowers
    override def uppers: SymbolicAlphabet = policyAlphabet.uppers

    override def toString: String = s"PolicyAlphabet { lowers = ${this.lowers.mkString}, uppers = ${this.uppers.mkString} }"

  /**
   * @return a policy alphabet with lowercase and uppercase characters and digits: 'a' to 'z' , 'A' to 'Z', '0' to '9'
   */
  def mediumAlphabet: PolicyAlphabet = new PolicyAlphabet:
    override def lowers: SymbolicAlphabet = policyAlphabet.lowers
    override def uppers: SymbolicAlphabet = policyAlphabet.uppers
    override def digits: SymbolicAlphabet = policyAlphabet.digits

    override def toString: String = s"PolicyAlphabet { lowers = ${this.lowers.mkString}, uppers = ${this.uppers.mkString}, digits = ${this.digits.mkString} }"
