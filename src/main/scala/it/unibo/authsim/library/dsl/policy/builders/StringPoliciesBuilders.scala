package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet
import it.unibo.authsim.library.dsl.policy.checkers.PolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

object StringPoliciesBuilders extends App:

  trait StringPolicyBuilder[T] extends Builder[T]:
    def addAlphabet(alphabetPolicy: PolicyAlphabet): StringPolicyBuilder[T]
    def addPatterns(regex: Regex): StringPolicyBuilder[T]
    def check(value: String)(implicit policyChecker: List[Regex] => PolicyChecker[String]): Boolean

  trait RestrictStringPolicyBuilder[T] extends Builder[T]:
    def maximumLength(number: Int): RestrictStringPolicyBuilder[T]
    def minimumLength(number: Int): RestrictStringPolicyBuilder[T]

  trait MoreRestrictStringPolicyBuilder[T] extends Builder[T]:
    def minimumLowerChars(number: Int): MoreRestrictStringPolicyBuilder[T]
    def minimumUpperChars(number: Int): MoreRestrictStringPolicyBuilder[T]
    def minimumSymbols(number: Int): MoreRestrictStringPolicyBuilder[T]
    def minimumNumbers(number: Int): MoreRestrictStringPolicyBuilder[T]

  abstract class AbstractStringPolicyBuilder[T] extends StringPolicyBuilder[T] with RestrictStringPolicyBuilder[T]:
    protected var minLen: Int = 1
    protected var maxLen: Int = 20
    protected var alphabetPolicy: PolicyAlphabet = new PolicyDefaultAlphabet
    protected var patterns: ListBuffer[Regex] = ListBuffer(this.alphabetPolicy.minimalLength)

    protected def checkNegativeNumbers(number: Int): Unit = require(number > 0, "number must be > 0")

    protected def checkReCall(regex: Regex): Unit =
      val regexString: String = regex.toString
      val index: Int = patterns.indexWhere(r => r.toString == regexString)
      if index != -1 then patterns.remove(index)

    override def addAlphabet(alphabetPolicy: PolicyAlphabet): AbstractStringPolicyBuilder[T] =
      this.alphabetPolicy = alphabetPolicy
      this

    override def addPatterns(regex: Regex): AbstractStringPolicyBuilder[T] =
      patterns += regex
      this

    override def maximumLength(number: Int): AbstractStringPolicyBuilder[T] =
      this.checkNegativeNumbers(number)
      this.checkReCall(this.alphabetPolicy.rangeLength(this.minLen, this.maxLen))
      require(number >= this.minLen , s"number must be >= ${this.minLen}")
      this.maxLen = number
      this.addPatterns(this.alphabetPolicy.rangeLength(this.minLen, number))
      this

    override def minimumLength(number: Int): AbstractStringPolicyBuilder[T] =
      this.checkNegativeNumbers(number)
      this.checkReCall(this.alphabetPolicy.minimumLength(this.minLen))
      require(number <= this.maxLen, s"number must be <= ${this.maxLen}")
      this.minLen = number
      this.addPatterns(this.alphabetPolicy.minimumLength(number))
      this

    override def check(value: String)(implicit policyChecker: List[Regex] => PolicyChecker[String]): Boolean = policyChecker(patterns.toList).check(value)

  abstract class AbstractMoreRestrictStringPolicyBuilder[T] extends AbstractStringPolicyBuilder[T] with MoreRestrictStringPolicyBuilder[T]:
    protected var minUpperChars: Int = 0
    protected var minLowerChars: Int = 0
    protected var minSymbols: Int = 0
    protected var minNumbers: Int = 0

    override def minimumLowerChars(number: Int): AbstractMoreRestrictStringPolicyBuilder[T] =
      this.checkNegativeNumbers(number)
      this.checkReCall(this.alphabetPolicy.minimumLowerCharacters(this.minLowerChars))
      this.minLowerChars = number
      this.patterns += this.alphabetPolicy.minimumLowerCharacters(number)
      this

    override def minimumUpperChars(number: Int): AbstractMoreRestrictStringPolicyBuilder[T] =
      this.checkNegativeNumbers(number)
      this.checkReCall(this.alphabetPolicy.minimumUpperCharacters(this.minUpperChars))
      this.minUpperChars = number
      this.addPatterns(this.alphabetPolicy.minimumUpperCharacters(number))
      this

    override def minimumSymbols(number: Int): AbstractMoreRestrictStringPolicyBuilder[T] =
      this.checkNegativeNumbers(number)
      this.checkReCall(this.alphabetPolicy.minimumSymbols(this.minSymbols))
      this.minSymbols = number
      this.addPatterns(this.alphabetPolicy.minimumSymbols(number))
      this

    override def minimumNumbers(number: Int): AbstractMoreRestrictStringPolicyBuilder[T] =
      this.checkNegativeNumbers(number)
      this.checkReCall(this.alphabetPolicy.minimumNumbers(this.minNumbers))
      this.minNumbers = number
      this.addPatterns(this.alphabetPolicy.minimumNumbers(number))
      this

  case class UserIDPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[UserIDPolicy]:
    override def build: UserIDPolicy = new UserIDPolicy:
      override def minimumLength: Int = UserIDPolicyBuilder.this.minLen
      override def maximumLength: Int = UserIDPolicyBuilder.this.maxLen
      override def alphabet: PolicyAlphabet = UserIDPolicyBuilder.this.alphabetPolicy
      override def patterns: ListBuffer[Regex] = UserIDPolicyBuilder.this.patterns
      override def minimumUpperChars: Int = UserIDPolicyBuilder.this.minUpperChars
      override def minimumLowerChars: Int = UserIDPolicyBuilder.this.minLowerChars
      override def minimumSymbols: Int = UserIDPolicyBuilder.this.minSymbols
      override def minimumNumbers: Int = UserIDPolicyBuilder.this.minNumbers
      override def toString: String = Helpers.buildToString("UseIDPolicy", this)

  case class PasswordPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[PasswordPolicy]:
    override def build: PasswordPolicy = new PasswordPolicy:
      override def minimumLength: Int = PasswordPolicyBuilder.this.minLen
      override def maximumLength: Int = PasswordPolicyBuilder.this.maxLen
      override def alphabet: PolicyAlphabet = PasswordPolicyBuilder.this.alphabetPolicy
      override def patterns: ListBuffer[Regex] = PasswordPolicyBuilder.this.patterns
      override def minimumUpperChars: Int = PasswordPolicyBuilder.this.minUpperChars
      override def minimumLowerChars: Int = PasswordPolicyBuilder.this.minLowerChars
      override def minimumSymbols: Int = PasswordPolicyBuilder.this.minSymbols
      override def minimumNumbers: Int = PasswordPolicyBuilder.this.minNumbers
      override def toString: String = Helpers.buildToString("PasswordPolicy", this)

  case class OTPPolicyBuilder() extends AbstractStringPolicyBuilder[OTPPolicy]:
    this.alphabetPolicy.onlyNumbers +=: this.patterns
    override def build: OTPPolicy = new OTPPolicy:
      override def minimumLength: Int = OTPPolicyBuilder.this.minLen
      override def maximumLength: Int = OTPPolicyBuilder.this.maxLen
      override def alphabet: PolicyAlphabet = OTPPolicyBuilder.this.alphabetPolicy
      override def patterns: ListBuffer[Regex] = OTPPolicyBuilder.this.patterns
      override def toString: String = Helpers.buildToString("OTPPolicy", this)

  case class SaltPolicyBuilder() extends AbstractMoreRestrictStringPolicyBuilder[SaltPolicy]:
    override def build: SaltPolicy = new SaltPolicy:
      override def minimumLength: Int = SaltPolicyBuilder.this.minLen
      override def maximumLength: Int = SaltPolicyBuilder.this.maxLen
      override def alphabet: PolicyAlphabet = SaltPolicyBuilder.this.alphabetPolicy
      override def patterns: ListBuffer[Regex] = SaltPolicyBuilder.this.patterns
      override def minimumUpperChars: Int = SaltPolicyBuilder.this.minUpperChars
      override def minimumLowerChars: Int = SaltPolicyBuilder.this.minLowerChars
      override def minimumSymbols: Int = SaltPolicyBuilder.this.minSymbols
      override def minimumNumbers: Int = SaltPolicyBuilder.this.minNumbers
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
        if policyMoreRestrict.getClass.getDeclaredMethods
                             .filter(m => m.getName!="minimumLength" && m.getName.startsWith("minimum"))
                             .exists(method => method.invoke(policyMoreRestrict).asInstanceOf[Int] > 0) then
          string.append(", ")
          if policyMoreRestrict.minimumUpperChars > 0 then
            string.append("minimum uppercase chars = ").append(policyMoreRestrict.minimumUpperChars).append(", ")
          if policyMoreRestrict.minimumLowerChars > 0 then
            string.append("minimum lowercase chars = ").append(policyMoreRestrict.minimumLowerChars).append(", ")
          if policyMoreRestrict.minimumSymbols > 0 then
            string.append("minimum symbols = ").append(policyMoreRestrict.minimumSymbols).append(", ")
          if policyMoreRestrict.minimumNumbers > 0 then
            string.append("minimum numbers = ").append(policyMoreRestrict.minimumNumbers)

      if stringPolicy.isInstanceOf[StringPolicy] then
        val policyString = stringPolicy.asInstanceOf[StringPolicy]
        string.append(", ")
          .append("patterns = ").append(policyString.patterns).append(", ")
          .append("alphabet = ").append(policyString.alphabet)

      string.append(" } ").toString
