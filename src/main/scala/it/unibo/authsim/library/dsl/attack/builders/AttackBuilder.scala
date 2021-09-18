package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer

import scala.concurrent.duration.Duration

trait AttackBuilder {
  private var statisticsConsumer: Option[StatisticsConsumer] = Option.empty
  private var timeout: Option[Duration] = Option.empty

  final def getStatisticsConsumer() = this.statisticsConsumer
  final def getTimeout() = this.timeout

  def logTo(statisticsConsumer: StatisticsConsumer): this.type = {
    this.statisticsConsumer = Option(statisticsConsumer)
    this
  }

  def timeout(time: Duration): this.type = {
    this.timeout = Option(time)
    this
  }

  def save(): Attack

  def executeNow(): Unit
}

trait Attack {
  def start(): Unit
}
