package it.unibo.authsim.library.user.builder

trait Builder[T]:
  def build: T|Option[T]
