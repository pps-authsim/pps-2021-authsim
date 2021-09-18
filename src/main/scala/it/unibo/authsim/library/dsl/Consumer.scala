package it.unibo.authsim.library.dsl

trait Consumer[T]:
  def consume(consumable: T): Unit
