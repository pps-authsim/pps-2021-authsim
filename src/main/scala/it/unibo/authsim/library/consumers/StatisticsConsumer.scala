package it.unibo.authsim.library.consumers

import it.unibo.authsim.library.attack.statistics.Statistics

/**
 * A consumer of Statistics.
 * Implementations of this type of consumer may not use all the values inside the consumed Statistics:
 * for example, one could only check if some users have been breached.
 */
trait StatisticsConsumer extends Consumer[Statistics]:
  override def consume(consumable: Statistics): Unit

/**
 * StatisticsConsumer companion object which provides a simple implementation which print on console the content of the consumed statistics.
 */
object StatisticsConsumer:
  def apply(): StatisticsConsumer = BasicStatisticsConsumer()

  private case class BasicStatisticsConsumer() extends StatisticsConsumer:
    override def consume(consumable: Statistics): Unit =
      println("Attempts: " + consumable.attempts)
      println("Elapsed time: " + consumable.elapsedTime.toMillis + " ms")
      println("Breached credentials: " + consumable.successfulBreaches.map(u => u.username + " - " + u.password).reduce((u1, u2) => u1 + "\n" + u2))
