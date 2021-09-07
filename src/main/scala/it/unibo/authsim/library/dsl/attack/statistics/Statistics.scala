package it.unibo.authsim.library.dsl.attack.statistics

import scala.concurrent.duration.Duration

class Statistics(val successfulValues: Set[String], val attempts: Long, val elapsedTime: Duration) {
  def +(other: Statistics): Statistics = {
    new Statistics(this.successfulValues ++ other.successfulValues, this.attempts + other.attempts, this.elapsedTime + other.elapsedTime)
  }
}