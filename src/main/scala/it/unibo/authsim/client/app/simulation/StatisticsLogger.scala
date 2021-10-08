package it.unibo.authsim.client.app.simulation

import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer

class StatisticsLogger(consumeStatistics: (Statistics => Unit)) extends StatisticsConsumer:

  override def consume(consumable: Statistics): Unit =
    consumeStatistics.apply(consumable)
