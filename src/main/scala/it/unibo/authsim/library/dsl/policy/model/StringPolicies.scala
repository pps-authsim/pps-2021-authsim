package it.unibo.authsim.library.dsl.policy.model

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.builders.PolicyAutoBuilder

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

object StringPolicies:

  trait StringPolicy:
    def alphabet: PolicyAlphabet
    def patterns: ListBuffer[Regex]
    def generate(implicit policyAutoBuilder: StringPolicy => PolicyAutoBuilder[String]): String = policyAutoBuilder(this).generate

  trait RestrictStringPolicy:
    def minimumLength: Int
    def maximumLength: Int

  trait MoreRestrictStringPolicy:
    def minimumUpperChars: Int
    def minimumLowerChars: Int
    def minimumSymbols: Int
    def minimumNumbers: Int

  sealed trait CredentialPolicy extends StringPolicy
  trait PasswordPolicy extends CredentialPolicy with RestrictStringPolicy with MoreRestrictStringPolicy
  trait UserIDPolicy extends CredentialPolicy with RestrictStringPolicy with MoreRestrictStringPolicy
  trait OTPPolicy extends CredentialPolicy with RestrictStringPolicy

  trait SaltPolicy extends StringPolicy with RestrictStringPolicy with MoreRestrictStringPolicy