package it.unibo.authsim.library.dsl.policy.checker

trait PolicyChecker[T]:
  def check(value: T): Boolean
