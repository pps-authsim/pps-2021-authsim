package it.unibo.authsim.library.policy.checkers

import it.unibo.authsim.library.policy.model.StringPolicies.StringPolicy

import scala.util.matching.Regex

/**
 * A ''StringPolicyChecker'' is an object that implements a [[PolicyChecker policy checker]] of type String
 */
object StringPolicyChecker:

  def apply(policy: StringPolicy): PolicyChecker[String] = implicitly[List[Regex] => PolicyChecker[String]].apply(policy.patterns.toList)