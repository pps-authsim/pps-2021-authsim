package it.unibo.authsim.library.dsl.policy.builders

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

trait StringPolicy:
  def patterns: ListBuffer[Regex]
  def minimumLength(number: Int): StringPolicy
  def maximumLength(number: Int): StringPolicy
  def minimumLowerChars(number: Int): StringPolicy
  def minimumUpperChars(number: Int): StringPolicy
  def minimumSymbols(number: Int): StringPolicy
  def minimumNumbers(number: Int): StringPolicy
  def addPatterns(regex: Regex): StringPolicy

object StringPolicy:

  class Builder(override val patterns: ListBuffer[Regex]) extends StringPolicy:

    private var minLen: Int = 1
    private var maxLen: Int = Int.MaxValue
    private var minUpperChars: Int = 0
    private var minLowerChars: Int = 0
    private var minSymbols: Int = 0
    private var minNumbers: Int = 0

    def this() = this(ListBuffer(RegexUtils.minimalLength))

    override def minimumLength(number: Int): StringPolicy =
      if number > this.maxLen then throw new IllegalArgumentException("number must be <" + this.maxLen)
      this.minLen = number
      this.addPatterns(RegexUtils.minimumLength(number))
      this

    override def maximumLength(number: Int): StringPolicy =
      if number < this.minLen then throw new IllegalArgumentException("number must be > " + this.minLen)
      this.maxLen = number
      this.addPatterns(RegexUtils.rangeLength(this.minLen, number))
      this

    override def minimumLowerChars(number: Int): StringPolicy =
      this.minLowerChars = number
      this.addPatterns(RegexUtils.minimumLowerCharacters(number))
      this

    override def minimumUpperChars(number: Int): StringPolicy =
      this.minUpperChars = number
      this.addPatterns(RegexUtils.minimumUpperCharacters(number))
      this

    override def minimumSymbols(number: Int): StringPolicy =
      this.minSymbols = number
      this.addPatterns(RegexUtils.minimumSymbols(number))
      this

    override def minimumNumbers(number: Int): StringPolicy =
      this.minNumbers = number
      this.addPatterns(RegexUtils.minimumNumbers(number))
      this

    override def addPatterns(regex: Regex): StringPolicy =
      patterns += regex
      this

    def getMinimumLength: Int = this.minLen

    def getMaximumLength: Int = this.maxLen

    def getMinimumUpperChars: Int = this.minUpperChars

    def getMinimumLowerChars: Int = this.minLowerChars

    def getMinimumSymbols: Int = this.minSymbols

    def getMinimumNumbers: Int = this.minNumbers

    override def toString: String =
      "StringPolicy.Builder { minimum length = " + this.getMinimumLength +
        ", maximum length = " + this.getMaximumLength +
        ", minimum uppercase chars = " + this.getMinimumUpperChars +
        ", minimum lowercase chars = " + this.getMinimumLowerChars +
        ", minimum symbols = " + this.getMinimumSymbols +
        ", minimum numbers = " + this.getMinimumNumbers +
        ", patterns " + this.patterns + " }"

  sealed trait CredentialPolicy extends Builder
  case class PasswordPolicy() extends CredentialPolicy
  case class UserIDPolicy() extends CredentialPolicy
  case class OTPPolicy() extends CredentialPolicy:
    override def minimumLowerChars(number: Int): StringPolicy = throw new UnsupportedOperationException
    override def minimumUpperChars(number: Int): StringPolicy = throw new UnsupportedOperationException
    override def minimumSymbols(number: Int): StringPolicy =  throw new UnsupportedOperationException

  case class Salt() extends Builder