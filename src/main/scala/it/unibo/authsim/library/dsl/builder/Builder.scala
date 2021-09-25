package it.unibo.authsim.library.dsl.builder

/**
 * This trait gives builders a method to simplify the definition of building methods
 * by returning automatically the self type.
 * @tparam S The type of the constructed object.
 */
trait Builder[+S]:
  /**
   * Applies the given function with the given value and returns the builder itself.
   *
   * @param f     The consuming function to apply to the argument.
   * @param value The argument for the function.
   * @tparam T The type of the variable consumed by the function.
   * @return The builder itself.
   */
  final protected def builderMethod[T](f: T => Unit)(value: T): this.type =
    f(value)
    this

  /**
   * @return The built object.
   */
  def build: S