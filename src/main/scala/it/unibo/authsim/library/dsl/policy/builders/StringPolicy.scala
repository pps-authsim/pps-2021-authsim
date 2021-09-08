package it.unibo.authsim.library.dsl.policy.builders

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

object StringPolicy:

  class Builder(val patterns: ListBuffer[Regex]):
    private var minLen: Int = 1
    private var maxLen: Int = Int.MaxValue

    def this() = this(ListBuffer(RegexUtils.minimalLength))

    def addPatterns(regex: Regex): Builder =
      patterns += regex
      this

    def maximumLength(number: Int): Builder =
      if number < this.minLen then throw new IllegalArgumentException("number must be > " + this.minLen)
      this.maxLen = number
      this.addPatterns(RegexUtils.rangeLength(this.minLen, number))
      this

    def minimumLength(number: Int): Builder =
      if number > this.maxLen then throw new IllegalArgumentException("number must be <" + this.maxLen)
      this.minLen = number
      this.addPatterns(RegexUtils.minimumLength(number))
      this

    def getMinimumLength: Int = this.minLen

    def getMaximumLength: Int = this.maxLen

    override def toString: String =
      "Policy.Builder { minimum length = " + this.getMinimumLength +
        ", maximum length = " + this.getMaximumLength +
        ", patterns = " + this.patterns + " }"

  trait OnlyCharsBuilder extends Builder:
    private var minUpperChars: Int = 0
    private var minLowerChars: Int = 0
    private var minSymbols: Int = 0
    private var minNumbers: Int = 0

    def minimumLowerChars(number: Int): OnlyCharsBuilder =
      this.minLowerChars = number
      this.addPatterns(RegexUtils.minimumLowerCharacters(number))
      this

    def minimumUpperChars(number: Int): OnlyCharsBuilder =
      this.minUpperChars = number
      this.addPatterns(RegexUtils.minimumUpperCharacters(number))
      this

    def minimumSymbols(number: Int): OnlyCharsBuilder =
      this.minSymbols = number
      this.addPatterns(RegexUtils.minimumSymbols(number))
      this

    def minimumNumbers(number: Int): OnlyCharsBuilder =
      this.minNumbers = number
      this.addPatterns(RegexUtils.minimumNumbers(number))
      this

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
        ", patterns = " + this.patterns + " }"

  sealed trait CredentialPolicy() extends Builder
  case class PasswordPolicy() extends CredentialPolicy with OnlyCharsBuilder
  case class UserIDPolicy() extends CredentialPolicy with OnlyCharsBuilder
  case class OTPPolicy() extends CredentialPolicy:
    this.addPatterns(RegexUtils.onlyNumbers)

  case class SaltPolicy() extends Builder with OnlyCharsBuilder
