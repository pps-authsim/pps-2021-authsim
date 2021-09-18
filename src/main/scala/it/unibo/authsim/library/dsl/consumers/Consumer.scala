package it.unibo.authsim.library.dsl.consumers

trait Consumer[T]:
  def consume(consumable: T): Unit
