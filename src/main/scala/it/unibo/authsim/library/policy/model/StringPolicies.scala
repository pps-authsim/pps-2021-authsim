package it.unibo.authsim.library.policy.model

import it.unibo.authsim.library.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.policy.generators.PolicyAutoGenerator

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

object StringPolicies:
  /**
   * ''StringPolicy'' rappresents a policy of the type string
   */
  trait StringPolicy:
    /**
     * @return alphabet of string policy
     */
    def alphabet: PolicyAlphabet
    /**
     * @return list of regular expression
     */
    def patterns: ListBuffer[Regex]
    /**
     * Generate a string based on the policy
 *
     * @param policyAutoGenerator (@see [[PolicyAutoGenerator#stringPolicyAutoGenerator]])
     * @return a random string that respects the string policy
     */
    def generate(implicit policyAutoGenerator: StringPolicy => PolicyAutoGenerator[String]): String = policyAutoGenerator(this).generate

  /**
   * ''RestrictStringPolicy'' rappresents a restriction on policies of the type string
   */
  trait RestrictStringPolicy:
    /**
     * @return minimum length that string must have
     */
    def minimumLength: Int
    /**
     * @return optional maximum length that string must have
     */
    def maximumLength: Option[Int]

  /**
   * ''MoreRestrictStringPolicy'' rappresents  an additional restriction for policies of the type string
   */
  trait MoreRestrictStringPolicy:
    /**
     * @return optional minimum number of uppercase characters a string must have
     */
    def minimumUpperChars: Option[Int]
    /**
     * @return optional minimum number of lowercase characters a string must have
     */
    def minimumLowerChars: Option[Int]
    /**
     * @return optional minimum number of symbols a string must have
     */
    def minimumSymbols: Option[Int]
    /**
     * @return optional minimum number of numbers a string must have
     */
    def minimumNumbers: Option[Int]

  /**
   * ''CredentialPolicy'' rappresent an abstraction of the users credential policy (userID, password, OTP(One Time Password), ... policy)
   */
  trait CredentialPolicy extends StringPolicy
  /**
   * ''PasswordPolicy'' is a policy that is used to build and/or check passwords
   */
  trait PasswordPolicy extends CredentialPolicy with RestrictStringPolicy with MoreRestrictStringPolicy
  /**
   * ''UserIDPolicy'' is a policy that is used to build and/or check userIDs
   */
  trait UserIDPolicy extends CredentialPolicy with RestrictStringPolicy with MoreRestrictStringPolicy
  /**
   * ''OTPPolicy'' is a policy that is used to build and/or check OTPs (One Time Passwords)
   */
  trait OTPPolicy extends CredentialPolicy with RestrictStringPolicy
  /**
   * ''SaltPolicy'' is a policy that is used to build and/or check salt values
   */
  trait SaltPolicy extends StringPolicy with RestrictStringPolicy with MoreRestrictStringPolicy