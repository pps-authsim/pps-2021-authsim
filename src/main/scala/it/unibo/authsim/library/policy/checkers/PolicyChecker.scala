package it.unibo.authsim.library.policy.checkers

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