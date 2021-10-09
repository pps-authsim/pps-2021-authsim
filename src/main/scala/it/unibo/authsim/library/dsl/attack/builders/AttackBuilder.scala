package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.builder.Builder
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer

import scala.concurrent.duration.Duration

/**
 * A builder for attacks, which can specify a timeout and to which StatisticsConsumer to log.
 */
trait AttackBuilder extends Builder[Attack]:
  private var statisticsConsumer: Option[StatisticsConsumer] = Option.empty
  private var timeout: Option[Duration] = Option.empty

  final def getStatisticsConsumer() = this.statisticsConsumer
  final def getTimeout() = this.timeout

  /**
   * Sets the consumer to log to.
   * @param statisticsConsumer The StatisticsConsumer to log to.
   * @return
   */
  def logTo(statisticsConsumer: StatisticsConsumer): this.type = this.builderMethod[StatisticsConsumer](consumer => this.statisticsConsumer = Option(consumer))(statisticsConsumer)

  /**
   * Sets the timeout for an attack.
   * @param time The amount of time to wait before declaring the timeout status.
   * @return
   */
  def timeout(time: Duration): this.type = this.builderMethod[Duration](time => this.timeout = Option(time))(time)

  /**
   * Builds the Attack and runs it.
   */
  def executeNow(): Unit = this.build.start()

/**
 * An attack, which can be started.
 */
trait Attack:
  /**
   * Starts the attack.
   */
  def start(): Unit
