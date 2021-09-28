package it.unibo.authsim.library.dsl.policy.builders

/**
 * A ''Builder'' is a generic trait to build a generic type.
 * @tparam T the type to build
 */
trait Builder[T]:
  /**
   * @return instance of type T
   */
  def build: T