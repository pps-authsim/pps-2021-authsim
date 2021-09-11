package it.unibo.authsim.library.dsl.attack.statistics

import it.unibo.authsim.library.user.User

import scala.concurrent.duration.Duration

class Statistics(val successfulBreaches: Set[User], val attempts: Long, val elapsedTime: Duration) {
  def +(other: Statistics): Statistics = {
    new Statistics(this.successfulBreaches ++ other.successfulBreaches, this.attempts + other.attempts, this.elapsedTime + other.elapsedTime)
  }
}

object Statistics {
  def zero: Statistics = new Statistics(Set(), attempts = 0, elapsedTime = Duration.Zero)
}