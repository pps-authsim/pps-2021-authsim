package it.unibo.authsim.library.dsl.consumers

import it.unibo.authsim.library.dsl.attack.statistics.Statistics

trait StatisticsConsumer extends Consumer[Statistics]:
  override def consume(consumable: Statistics): Unit

object StatisticsConsumer:
  def apply(): StatisticsConsumer = BasicStatisticsConsumer()

  private case class BasicStatisticsConsumer() extends StatisticsConsumer:
    override def consume(consumable: Statistics): Unit =
      println("Attempts: " + consumable.attempts)
      println("Elapsed time: " + consumable.elapsedTime.toMillis + " ms")
      println("Breached credentials: " + consumable.successfulBreaches.map(u => u.username + " - " + u.password).reduce((u1, u2) => u1 + "\n" + u2))
