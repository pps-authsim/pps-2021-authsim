package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer

import scala.concurrent.duration.Duration

/**
 * A builder for attacks, which can specify a timeout and to which StatisticsConsumer to log.
 */
trait AttackBuilder:
  private var statisticsConsumer: Option[StatisticsConsumer] = Option.empty
  private var timeout: Option[Duration] = Option.empty

  final def getStatisticsConsumer() = this.statisticsConsumer
  final def getTimeout() = this.timeout

  /**
   * Sets the consumer to log to.
   * @param statisticsConsumer The StatisticsConsumer to log to.
   * @return The builder
   */
  def logTo(statisticsConsumer: StatisticsConsumer): this.type =
    this.statisticsConsumer = Option(statisticsConsumer)
    this

  /**
   * Sets the timeout for an attack.
   * @param time The amount of time to wait before declaring the timeout status.
   * @return The builder
   */
  def timeout(time: Duration): this.type =
    this.timeout = Option(time)
    this

  /**
   * Builds the Attack with the defined parameters.
   * @return The built Attack
   */
  def save(): Attack

  /**
   * Builds the Attack and runs it.
   */
  def executeNow(): Unit

/**
 * An attack, which can be started.
 */
trait Attack:
  /**
   * Starts the attack.
   */
  def start(): Unit
