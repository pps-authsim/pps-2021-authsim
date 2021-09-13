package it.unibo.authsim.library.dsl

sealed trait Protocol(val port: Option[Int])
object Protocol:
  case class Local() extends Protocol(None)
  case class Http() extends Protocol(Some(80))
  case class Https() extends Protocol(Some(43))
  case class Ssh() extends Protocol(Some(22))

