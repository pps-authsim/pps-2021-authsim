package it.unibo.authsim.library.policy.builders.stringpolicy

import it.unibo.authsim.library.alphabet.SymbolicAlphabet
import it.unibo.authsim.library.builder.Builder
import it.unibo.authsim.library.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.policy.alphabet.PolicyAlphabet.{PolicyDefaultAlphabet, PolicyOTPAlphabet}
import it.unibo.authsim.library.policy.builders.stringpolicy.StringPolicyBuildersHelpers
import it.unibo.authsim.library.policy.checkers.PolicyChecker
import it.unibo.authsim.library.policy.model.StringPolicies.*

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
 * ''StringPolicyBuilder'' is a trait that is used to build a new policy of type string
 * @tparam T the type to build
 */
trait StringPolicyBuilder[T] extends Builder[T]

object StringPolicyBuilder:
  /**
   * ''SettableAlphabetBuilder'' rappresent an extension to build a new policy of type string with another alphabet
   */
  trait SettableAlphabetBuilder:
    /**
     * Set a [[PolicyAlphabet policy alphabet]]
     * @param alphabetPolicy policy alphabet to set
     * @return instance of the actual builder
     */
    def addAlphabet(alphabetPolicy: PolicyAlphabet): this.type

  /**
   * ''RestrictStringPolicyBuilder'' rappresent an extension to build a new restricted policy of type string
   */
  trait RestrictStringPolicyBuilder:
    /**
     * Set the maximum length a string must have.
     * @param number maximum length of string to set
     * @return instance of the actual builder
     */
    def maximumLength(number: Int): this.type
    /**
     * Set the minimum length a string must have.
     * @param number minimum length of string to set
     * @return instance of the actual builder
     */
    def minimumLength(number: Int): this.type

  /**
   * ''MoreRestrictStringPolicyBuilder'' rappresent an extension to build a new more restricted policy of type string
   */
  trait MoreRestrictStringPolicyBuilder:
    /**
     * Set the minimum number of lowercase characters a string must have.
     * @param number minimum number of lowercase characters to set
     * @return instance of the actual builder
     */
    def minimumLowerChars(number: Int): this.type
    /**
     * Set the minimum number of uppercase characters a string must have.
     * @param number minimum number of uppercase characters to set
     * @return instance of the actual builder
     */
    def minimumUpperChars(number: Int): this.type
    /**
     * Set the minimum number of symbols a string must have.
     * @param number minimum number of symbols to set
     * @return instance of the actual builder
     */
    def minimumSymbols(number: Int): this.type
    /**
     * Set the minimum number of digits a string must have.
     * @param number minimum number of digits to set
     * @return instance of the actual builder
     */
    def minimumNumbers(number: Int): this.type

  /**
   * ''AbstractStringPolicyBuilder'' is an abstract builder that implements [[StringPolicyBuilder]] and [[RestrictStringPolicyBuilder]] methods
   * @tparam T the type to build
   */
  abstract class AbstractStringPolicyBuilder[T] extends StringPolicyBuilder[T] with RestrictStringPolicyBuilder:
    protected var minLen: Int = 1
    protected var maxLen: Option[Int] = None
    protected var alphabetPolicy: PolicyAlphabet = null
    protected var patterns: ListBuffer[Regex] = ListBuffer.empty
    this.setAlphabet(PolicyDefaultAlphabet())

    protected def checkNegativeNumbers(number: Int): Unit = require(number > 0, "number must be > 0")

    protected def checkReCall(regex: Regex): Unit =
      val regexString: String = regex.toString
      val index: Int = patterns.indexWhere(r => r.toString == regexString)
      if index != -1 then patterns.remove(index)

    protected def checker(number: Int)(regex: Regex): Unit =
      this.checkNegativeNumbers(number)
      this.checkReCall(regex)

    protected def addPatterns(regex: Regex) = this.builderMethod((regex: Regex) => patterns += regex)(regex)

    protected def setAlphabet(alphabetPolicy: PolicyAlphabet) =
      this.builderMethod((alphabetPolicy: PolicyAlphabet) =>
        if patterns.nonEmpty then patterns.remove(0)
        this.alphabetPolicy = alphabetPolicy;
        patterns.insert(0, this.alphabetPolicy.minimalLength)
      )(alphabetPolicy)

    /**
     * @param number maximum length of string to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0 or whether number is less than minimum value
     */
    override def maximumLength(number: Int) = this.builderMethod((number: Int) => {
      this.checker(number)(this.alphabetPolicy.rangeLength(this.minLen, this.maxLen.getOrElse(this.minLen)))
      require(number >= this.minLen , s"number must be >= ${this.minLen}")
      this.maxLen = Some(number)
      this.addPatterns(this.alphabetPolicy.rangeLength(this.minLen, number))
    })(number)

    /**
     * @param number minimum length of string to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0 or whether number is greater than maximum value
     */
    override def minimumLength(number: Int) = this.builderMethod((number: Int) => {
      this.checker(number)(this.alphabetPolicy.minimumLength(this.minLen))
      if this.maxLen.isDefined then require(number <= this.maxLen.get, s"number must be <= ${this.maxLen.get}")
      this.minLen = number
      this.addPatterns(this.alphabetPolicy.minimumLength(number))
    })(number)

  /**
   * ''AbstractStringPolicyWithSettableAlphabetBuilder'' is an abstract builder that extends [[AbstractStringPolicyBuilder]] and implements [[SettableAlphabetBuilder]] methods
   * @tparam T the type to build
   */
  abstract class AbstractStringPolicyBuilderWithSettableAlphabet[T] extends AbstractStringPolicyBuilder[T] with SettableAlphabetBuilder:
    override def addAlphabet(alphabetPolicy: PolicyAlphabet) = this.builderMethod((alphabetPolicy: PolicyAlphabet) => super.setAlphabet(alphabetPolicy))(alphabetPolicy)

  /**
   * ''AbstractMoreRestrictStringPolicyBuilder'' is an abstract builder that extends [[AbstractStringPolicyBuilderWithSettableAlphabet]] and implements [[MoreRestrictStringPolicyBuilder]] methods
   * @tparam T the type to build
   */
  abstract class AbstractMoreRestrictStringPolicyBuilder[T] extends AbstractStringPolicyBuilderWithSettableAlphabet[T] with MoreRestrictStringPolicyBuilder:
    protected var minUpperChars: Option[Int] = None
    protected var minLowerChars: Option[Int] = None
    protected var minSymbols: Option[Int] = None
    protected var minNumbers: Option[Int] = None

    private enum What:
      case LOWER, UPPER, SYMBOL, NUMBER, MAX, MIN

    private def checkLengthWith(what: What)(value: Option[Int])(min: Int = this.minLen, max: Option[Int] = this.maxLen): Unit =
      var length: Int = value.getOrElse(0) + (what match {
        case What.LOWER => this.minUpperChars.getOrElse(0) + this.minSymbols.getOrElse(0) + this.minNumbers.getOrElse(0)
        case What.UPPER => this.minLowerChars.getOrElse(0) + this.minSymbols.getOrElse(0) + this.minNumbers.getOrElse(0)
        case What.SYMBOL => this.minLowerChars.getOrElse(0) + this.minUpperChars.getOrElse(0) + this.minNumbers.getOrElse(0)
        case What.NUMBER => this.minLowerChars.getOrElse(0) + this.minUpperChars.getOrElse(0) + this.minSymbols.getOrElse(0)

        case What.MIN | What.MAX => this.minLowerChars.getOrElse(0) + this.minUpperChars.getOrElse(0) + this.minSymbols.getOrElse(0) + this.minNumbers.getOrElse(0)
      })
      if max.isDefined then require(length <= max.get, s"sum of minimum (lowercase, uppercase, symbols, numbers) must be less or equals than ${max.get}")

    /**
     * @param number minimum length of string to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0 or whether number is greater than maximum value or
     *                                  the sum of minimum (lowecase, uppercase, symbols and numbers) is greater than maximum length
     */
    override def minimumLength(number: Int) =
      this.checkLengthWith(What.MIN)(None)(number)
      super.minimumLength(number)

    /**
     * @param number maximum length of string to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0 or whether number is less than minimum value or
     *                                  the sum of minimum (lowecase, uppercase, symbols and numbers) is greater than maximum length
     */
    override def maximumLength(number: Int) =
      this.checkLengthWith(What.MAX)(None)(this.minLen, Some(number))
      super.maximumLength(number)

    /**
     * @param number minimum number of lowercase characters to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0 or
     *                                  the sum of minimum (lowecase, uppercase, symbols and numbers) is greater than maximum length
     */
    override def minimumLowerChars(number: Int) = this.builderMethod((number: Int) => {
      this.checkLengthWith(What.LOWER)(Some(number))()
      this.checker(number)(this.alphabetPolicy.minimumLowerCharacters(this.minLowerChars.getOrElse(0)))
      this.minLowerChars = Some(number)
      this.patterns += this.alphabetPolicy.minimumLowerCharacters(number)
    })(number)

    /**
     * @param number minimum number of uppercase characters to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0 or
     *                                  the sum of minimum (lowecase, uppercase, symbols and numbers) is greater than maximum length
     */
    override def minimumUpperChars(number: Int) = this.builderMethod((number: Int) => {
      this.checkLengthWith(What.UPPER)(Some(number))()
      this.checker(number)(this.alphabetPolicy.minimumUpperCharacters(this.minUpperChars.getOrElse(0)))
      this.minUpperChars = Some(number)
      this.addPatterns(this.alphabetPolicy.minimumUpperCharacters(number))
    })(number)

    /**
     * @param number minimum number of symbols to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0 or
     *                                  the sum of minimum (lowecase, uppercase, symbols and numbers) is greater than maximum length
     */
    override def minimumSymbols(number: Int) = this.builderMethod((number: Int) => {
      this.checkLengthWith(What.SYMBOL)(Some(number))()
      this.checker(number)(this.alphabetPolicy.minimumSymbols(this.minSymbols.getOrElse(0)))
      this.minSymbols = Some(number)
      this.addPatterns(this.alphabetPolicy.minimumSymbols(number))
    })(number)

    /**
     * @param number minimum number of digits to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0 or
     *                                  the sum of minimum (lowecase, uppercase, symbols and numbers) is greater than maximum length
     */
    override def minimumNumbers(number: Int) = this.builderMethod((number: Int) => {
      this.checkLengthWith(What.NUMBER)(Some(number))()
      this.checker(number)(this.alphabetPolicy.minimumNumbers(this.minNumbers.getOrElse(0)))
      this.minNumbers = Some(number)
      this.addPatterns(this.alphabetPolicy.minimumNumbers(number))
    })(number)
