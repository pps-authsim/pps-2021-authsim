package it.unibo.authsim.library.dsl.policy.checker

import it.unibo.authsim.library.dsl.policy.builders.StringPolicy

trait PolicyChecker[T]:
  def check(value: T): Boolean
