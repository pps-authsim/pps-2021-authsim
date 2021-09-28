package it.unibo.authsim.library.dsl.policy.checkers

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.StringPolicy

import scala.util.matching.Regex

/**
 * A ''StringPolicyChecker'' is an object that implements a [[PolicyChecker policy checker]] of type String
 */
object StringPolicyChecker:

  def apply(policy: StringPolicy): PolicyChecker[String] = new PolicyChecker[String]:
    override def check(value: String): Boolean =
      implicitly[List[Regex] => PolicyChecker[String]].apply(policy.patterns.toList).check(value)