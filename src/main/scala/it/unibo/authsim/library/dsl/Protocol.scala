package it.unibo.authsim.library.dsl

/**
 * ''Protocol'' is a trait that is used to define a transmission protocol with default port ''port''
 */
sealed trait Protocol(val port: Int)
object Protocol:
  /**
   * ''Http'' rappresent the HTTP protocol with default port 80
   */
  case class Http() extends Protocol(80)
  /**
   * ''Https'' rappresent the HTTPS protocol with default port 43
   */
  case class Https() extends Protocol(43)
  /**
   * ''Ssh'' rappresent the SSH protocol with default port 22
   */
  case class Ssh() extends Protocol(22)

