package it.unibo.authsim.library.attack.builders

import it.unibo.authsim.library.builder.Builder
import it.unibo.authsim.library.consumers.StatisticsConsumer

import scala.concurrent.duration.Duration

/**
 * A builder for attacks, which can specify a timeout and to which StatisticsConsumer to log, both of which are optional.
 * If the consumner is not specified, the logging does not take place.
 * If the timeout is not specified, the attack will continue to run until the end of the inputs.
 */
@throws[IllegalArgumentException]("Not all mandatory fields have been configured.")
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
