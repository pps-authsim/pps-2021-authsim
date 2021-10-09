package it.unibo.authsim.library.dsl.policy.checkers

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.StringPolicy

import scala.util.matching.Regex

/**
 * A ''StringPolicyChecker'' is an object that implements a [[PolicyChecker policy checker]] of type String
 */
object StringPolicyChecker:

  def apply(policy: StringPolicy): PolicyChecker[String] = BasicStringPolicyChecker(policy)

  private case class BasicStringPolicyChecker(policy: StringPolicy) extends PolicyChecker[String]:
    require(policy != null, "policy must be initialized")
    override def check(value: String): Boolean = policy.patterns forall { _.matches(value.trim) }