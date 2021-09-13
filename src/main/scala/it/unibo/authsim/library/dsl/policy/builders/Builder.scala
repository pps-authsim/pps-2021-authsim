package it.unibo.authsim.library.dsl.policy.builders

trait Builder[T]:
  def build: T