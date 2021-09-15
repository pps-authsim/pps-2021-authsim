package it.unibo.authsim.library.dsl.attack.statistics

import it.unibo.authsim.library.user.model.User
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.{Duration, MINUTES}

class StatisticsTest extends AnyWordSpec {
  class SimpleUser(val username: String, val password: String) extends User {}

  val invariantStats = Statistics.zero
  val simpleStats = new Statistics(Set(new SimpleUser("mario", "authsim")), 1_000_000, Duration(2, MINUTES))
  val anotherStats = new Statistics(Set(new SimpleUser("luca", "pps2021")), 3_000_000, Duration(7, MINUTES))

  "The invariant stats" when {
    "summed to another statistics" must {
      val sumStats = invariantStats + simpleStats
      "not change the other statistics values" in {
        assert(sumStats.successfulBreaches.equals(simpleStats.successfulBreaches) &&
          sumStats.attempts.equals(simpleStats.attempts) &&
          sumStats.elapsedTime.equals(simpleStats.elapsedTime))
      }
    }
    "summed to itself" must {
      val summedInvariants = invariantStats + invariantStats
      "return itself" in {
        assert(summedInvariants.successfulBreaches.equals(invariantStats.successfulBreaches) &&
          summedInvariants.attempts.equals(invariantStats.attempts) &&
          summedInvariants.elapsedTime.equals(invariantStats.elapsedTime))
      }
    }
  }

  "Two non-invariant statistics" when {
    "summed" must {
      val summedStatistics = simpleStats + anotherStats
      "include the union of results" in {
        assert(summedStatistics.successfulBreaches.equals(simpleStats.successfulBreaches ++ anotherStats.successfulBreaches) &&
          summedStatistics.attempts.equals(simpleStats.attempts + anotherStats.attempts) &&
          summedStatistics.elapsedTime.equals(simpleStats.elapsedTime + anotherStats.elapsedTime))
      }
    }
  }
}
