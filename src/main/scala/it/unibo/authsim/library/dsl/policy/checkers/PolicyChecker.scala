package it.unibo.authsim.library.dsl.policy.checkers

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.StringPolicyBuilder

import scala.util.matching.Regex

/**
 * A ''PolicyChecker'' is a trait that is used to define a policy checker
 * @tparam T value of the policy
 */
trait PolicyChecker[T]:
  /**
   * Check if the given value is valid for a policy of the type T
   * @param value value to check 
   * @return true if the given ''value'' is a valid value for a policy of the type T, otherwise false
   */
  def check(value: T): Boolean

object PolicyChecker:
  /**
   *  Implicitly converts a list of regular expressions of a policy into an instance of [[PolicyChecker policy checker]] of type String
   */
  implicit val stringPolicyChecker: List[Regex] => PolicyChecker[String] =
    (patterns: List[Regex]) =>
      new PolicyChecker[String]:
        override def check(value: String): Boolean = patterns forall { _.matches(value.trim) }
