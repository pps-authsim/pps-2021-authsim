package it.unibo.authsim.library.dsl.attack.builders

import scala.concurrent.duration.Duration
import it.unibo.authsim.library.dsl.attack.logspecification.LogSpec

trait AttackBuilder {
  private var logSpecification: Option[LogSpec] = Option.empty
  private var timeout: Option[Duration] = Option.empty

  final def getLogSpecification() = this.logSpecification
  final def getTimeout() = this.timeout

  def log(logSpecification: LogSpec): AttackBuilder = {
    this.logSpecification = Option(logSpecification)
    this
  }

  def timeout(time: Duration): AttackBuilder = {
    this.timeout = Option(time)
    this
  }

  def save(): Attack

  def executeNow(): Unit
}

trait Attack {
  def start(): Unit
}
