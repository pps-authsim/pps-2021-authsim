package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet
import it.unibo.authsim.library.dsl.policy.checkers.PolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*
import it.unibo.authsim.library.dsl.builder.Builder

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

object StringPoliciesBuilders:

  /**
   * ''StringPolicyBuilder'' is a trait that is used to build a new policy of type string
   * @tparam T the type to build
   */
  trait StringPolicyBuilder[T] extends Builder[T]:
    /**
     * Set a [[PolicyAlphabet policy alphabet]]
     * @param alphabetPolicy policy alphabet to set
     * @return instance of the actual builder
     */
    def addAlphabet(alphabetPolicy: PolicyAlphabet): this.type
    /**
     * Check if the given value is valid for a policy of type string
     * @param value value to check
     * @param policyChecker (@see [[PolicyChecker#stringPolicyChecker]])
     * @return instance of the actual builder
     */
    def check(value: String)(implicit policyChecker: List[Regex] => PolicyChecker[String]): Boolean

  /**
   * ''RestrictStringPolicyBuilder'' rappresent an extension to build a new restricted policy of type string
   * @tparam T the type to build
   */
  trait RestrictStringPolicyBuilder[T] extends Builder[T]:
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
   * @tparam T the type to build
   */
  trait MoreRestrictStringPolicyBuilder[T] extends Builder[T]:
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
  abstract class AbstractStringPolicyBuilder[T] extends StringPolicyBuilder[T] with RestrictStringPolicyBuilder[T]:
    protected var minLen: Int = 1
    protected var maxLen: Option[Int] = None
    protected var alphabetPolicy: PolicyAlphabet = new PolicyDefaultAlphabet
    protected var patterns: ListBuffer[Regex] = ListBuffer(this.alphabetPolicy.minimalLength)

    protected def checkNegativeNumbers(number: Int): Unit = require(number > 0, "number must be > 0")

    protected def checkReCall(regex: Regex): Unit =
      val regexString: String = regex.toString
      val index: Int = patterns.indexWhere(r => r.toString == regexString)
      if index != -1 then patterns.remove(index)

    protected def checker(number: Int)(regex: Regex): Unit =
      this.checkNegativeNumbers(number)
      this.checkReCall(regex)

    protected def addPatterns(regex: Regex) = this.builderMethod((regex: Regex) => patterns += regex)(regex)

    override def addAlphabet(alphabetPolicy: PolicyAlphabet) =
      this.builderMethod((alphabetPolicy: PolicyAlphabet) => this.alphabetPolicy = alphabetPolicy)(alphabetPolicy)

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

    override def check(value: String)(implicit policyChecker: List[Regex] => PolicyChecker[String]): Boolean = policyChecker(patterns.toList).check(value)

  /**
   * ''AbstractMoreRestrictStringPolicyBuilder'' is an abstract builder that extends [[AbstractStringPolicyBuilder]] and implements [[MoreRestrictStringPolicyBuilder]] methods
   * @tparam T the type to build
   */
  abstract class AbstractMoreRestrictStringPolicyBuilder[T] extends AbstractStringPolicyBuilder[T] with MoreRestrictStringPolicyBuilder[T]:
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
//      println(s"Length = $length, range [$min, ${if max.isDefined then max.get else "..."}]")
      if max.isDefined then require(length <= max.get, s"sum of minimum (lowercase, uppercase, symbols, numbers) must be less or equals than ${max.get}")

    override def minimumLength(number: Int) =
      this.checkLengthWith(What.MIN)(None)(number)
      super.minimumLength(number)

    override def maximumLength(number: Int) =
      this.checkLengthWith(What.MAX)(None)(this.minLen, Some(number))
      super.maximumLength(number)

    /**
     * @param number minimum number of lowercase characters to set
     * @return instance of the actual builder
     * @throws IllegalArgumentException whether number is less than or equal to 0
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
     * @throws IllegalArgumentException whether number is less than or equal to 0
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
     * @throws IllegalArgumentException whether number is less than or equal to 0
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
     * @throws IllegalArgumentException whether number is less than or equal to 0
     */
    override def minimumNumbers(number: Int) = this.builderMethod((number: Int) => {
      this.checkLengthWith(What.NUMBER)(Some(number))()
      this.checker(number)(this.alphabetPolicy.minimumNumbers(this.minNumbers.getOrElse(0)))
      this.minNumbers = Some(number)
      this.addPatterns(this.alphabetPolicy.minimumNumbers(number))
    })(number)

  /**
   * ''UserIDPolicyBuilder'' is userID policy builder
   */
  case class UserIDPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[UserIDPolicy]:
    override def build: UserIDPolicy = new UserIDPolicy:
      override def minimumLength: Int = UserIDPolicyBuilder.this.minLen
      override def maximumLength: Option[Int] = UserIDPolicyBuilder.this.maxLen
      override def alphabet: PolicyAlphabet = UserIDPolicyBuilder.this.alphabetPolicy
      override def patterns: ListBuffer[Regex] = UserIDPolicyBuilder.this.patterns
      override def minimumUpperChars: Option[Int] = UserIDPolicyBuilder.this.minUpperChars
      override def minimumLowerChars: Option[Int] = UserIDPolicyBuilder.this.minLowerChars
      override def minimumSymbols: Option[Int] = UserIDPolicyBuilder.this.minSymbols
      override def minimumNumbers: Option[Int] = UserIDPolicyBuilder.this.minNumbers
      override def toString: String = Helpers.buildToString("UseIDPolicy", this)

  /**
   * ''PasswordPolicyBuilder'' is password policy builder
   */
  case class PasswordPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[PasswordPolicy]:
    override def build: PasswordPolicy = new PasswordPolicy:
      override def minimumLength: Int = PasswordPolicyBuilder.this.minLen
      override def maximumLength: Option[Int] = PasswordPolicyBuilder.this.maxLen
      override def alphabet: PolicyAlphabet = PasswordPolicyBuilder.this.alphabetPolicy
      override def patterns: ListBuffer[Regex] = PasswordPolicyBuilder.this.patterns
      override def minimumUpperChars: Option[Int] = PasswordPolicyBuilder.this.minUpperChars
      override def minimumLowerChars: Option[Int] = PasswordPolicyBuilder.this.minLowerChars
      override def minimumSymbols: Option[Int] = PasswordPolicyBuilder.this.minSymbols
      override def minimumNumbers: Option[Int] = PasswordPolicyBuilder.this.minNumbers
      override def toString: String = Helpers.buildToString("PasswordPolicy", this)

  /**
   * ''OTPPolicyBuilder'' is OTP policy builder
   */
  case class OTPPolicyBuilder() extends AbstractStringPolicyBuilder[OTPPolicy]:
    this.alphabetPolicy.onlyNumbers +=: this.patterns
    override def build: OTPPolicy = new OTPPolicy:
      override def minimumLength: Int = OTPPolicyBuilder.this.minLen
      override def maximumLength: Option[Int] = OTPPolicyBuilder.this.maxLen
      override def alphabet: PolicyAlphabet = OTPPolicyBuilder.this.alphabetPolicy
      override def patterns: ListBuffer[Regex] = OTPPolicyBuilder.this.patterns
      override def toString: String = Helpers.buildToString("OTPPolicy", this)

  /**
   * ''SaltPolicyBuilder'' is salt policy builder
   */
  case class SaltPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[SaltPolicy]:
    override def build: SaltPolicy = new SaltPolicy:
      override def minimumLength: Int = SaltPolicyBuilder.this.minLen
      override def maximumLength: Option[Int] = SaltPolicyBuilder.this.maxLen
      override def alphabet: PolicyAlphabet = SaltPolicyBuilder.this.alphabetPolicy
      override def patterns: ListBuffer[Regex] = SaltPolicyBuilder.this.patterns
      override def minimumUpperChars: Option[Int] = SaltPolicyBuilder.this.minUpperChars
      override def minimumLowerChars: Option[Int] = SaltPolicyBuilder.this.minLowerChars
      override def minimumSymbols: Option[Int] = SaltPolicyBuilder.this.minSymbols
      override def minimumNumbers: Option[Int] = SaltPolicyBuilder.this.minNumbers
      override def toString: String = Helpers.buildToString("SaltPolicy", this)

  private object Helpers:
    def buildToString(name: String, stringPolicy: StringPolicy | RestrictStringPolicy | MoreRestrictStringPolicy): String =
      val string: StringBuilder = new StringBuilder(name).append(" { ")

      if stringPolicy.isInstanceOf[RestrictStringPolicy] then
        val policyRestrict = stringPolicy.asInstanceOf[RestrictStringPolicy]
        string
          .append("minimum length = ").append(policyRestrict.minimumLength).append(", ")
          .append("maximum length = ").append(policyRestrict.maximumLength)

      if stringPolicy.isInstanceOf[MoreRestrictStringPolicy] then
        val policyMoreRestrict: MoreRestrictStringPolicy = stringPolicy.asInstanceOf[MoreRestrictStringPolicy]
        string.append(", ").append("minimum uppercase chars = ").append(policyMoreRestrict.minimumUpperChars)
        string.append(", ").append("minimum lowercase chars = ").append(policyMoreRestrict.minimumLowerChars)
        string.append(", ").append("minimum symbols = ").append(policyMoreRestrict.minimumSymbols)
        string.append(", ").append("minimum numbers = ").append(policyMoreRestrict.minimumNumbers)

      if stringPolicy.isInstanceOf[StringPolicy] then
        val policyString = stringPolicy.asInstanceOf[StringPolicy]
        string.append(", ")
          .append("patterns = ").append(policyString.patterns).append(", ")
          .append("alphabet = ").append(policyString.alphabet)

      string.append(" } ").toString
