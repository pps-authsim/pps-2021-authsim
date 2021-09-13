package it.unibo.authsim.library.dsl.policy.model

import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

object StringPolicies:

  trait StringPolicy:
    def alphabet: PolicyAlphabet
    def patterns: ListBuffer[Regex]

  trait RestrictStringPolicy:
    def minimumLength: Int
    def maximumLength: Int

  trait MoreRestrictStringPolicy:
    def minimumUpperChars: Int
    def minimumLowerChars: Int
    def minimumSymbols: Int
    def minimumNumbers: Int

  sealed trait CredentialPolicy
  trait PasswordPolicy extends CredentialPolicy with StringPolicy with RestrictStringPolicy with MoreRestrictStringPolicy
  trait UserIDPolicy extends CredentialPolicy with StringPolicy with RestrictStringPolicy with MoreRestrictStringPolicy
  trait OTPPolicy extends CredentialPolicy with StringPolicy with RestrictStringPolicy

  trait SaltPolicy extends StringPolicy with RestrictStringPolicy with MoreRestrictStringPolicy