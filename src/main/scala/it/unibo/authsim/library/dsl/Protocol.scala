package it.unibo.authsim.library.dsl

sealed trait Protocol(val port: Int)
object Protocol:
  case class Http() extends Protocol(80)
  case class Https() extends Protocol(43)
  case class Ssh() extends Protocol(22)

