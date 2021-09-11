package it.unibo.authsim.library.dsl.policy.auto.generator

trait PolicyAutoBuilder[T]:
  def generate: T
