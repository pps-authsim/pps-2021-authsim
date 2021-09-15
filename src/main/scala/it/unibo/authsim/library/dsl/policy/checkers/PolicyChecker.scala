package it.unibo.authsim.library.dsl.policy.checkers

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.StringPolicyBuilder

import scala.util.matching.Regex

trait PolicyChecker[T]:
  def check(value: T): Boolean

object PolicyChecker:
  implicit val stringPolicyChecker: List[Regex] => PolicyChecker[String] =
    (patterns: List[Regex]) =>
      new PolicyChecker[String]:
        override def check(value: String): Boolean = patterns forall { _.matches(value.trim) }
