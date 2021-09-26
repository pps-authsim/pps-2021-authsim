package it.unibo.authsim.library.user.builder

trait OptionalBuilder[U]:
  def build():Option[U]