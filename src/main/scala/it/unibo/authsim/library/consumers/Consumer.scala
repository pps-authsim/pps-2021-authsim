package it.unibo.authsim.library.consumers

/**
 * A consumer of an information.
 * @tparam T The type of the information.
 */
trait Consumer[T]:
  def consume(consumable: T): Unit
