package it.unibo.authsim.library.dsl.attack.statistics

import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.{Duration, MINUTES}

class StatisticsTest extends AnyWordSpec {
  val invariantStats = new Statistics(Set(), attempts = 0, Duration.Zero)
  val simpleStats = new Statistics(Set("authsim"), 1_000_000, Duration(2, MINUTES))
  val anotherStats = new Statistics(Set("pps2021"), 3_000_000, Duration(7, MINUTES))

  "The invariant stats" when {
    "summed to another statistics" must {
      val sumStats = invariantStats + simpleStats
      "not change the other statistics values" in {
        assert(sumStats.successfulValues.equals(simpleStats.successfulValues) &&
          sumStats.attempts.equals(simpleStats.attempts) &&
          sumStats.elapsedTime.equals(simpleStats.elapsedTime))
      }
    }
    "summed to itself" must {
      val summedInvariants = invariantStats + invariantStats
      "return itself" in {
        assert(summedInvariants.successfulValues.equals(invariantStats.successfulValues) &&
          summedInvariants.attempts.equals(invariantStats.attempts) &&
          summedInvariants.elapsedTime.equals(invariantStats.elapsedTime))
      }
    }
  }

  "Two non-invariant statistics" when {
    "summed" must {
      val summedStatistics = simpleStats + anotherStats
      "include the union of results" in {
        assert(summedStatistics.successfulValues.equals(simpleStats.successfulValues ++ anotherStats.successfulValues) &&
          summedStatistics.attempts.equals(simpleStats.attempts + anotherStats.attempts) &&
          summedStatistics.elapsedTime.equals(simpleStats.elapsedTime + anotherStats.elapsedTime))
      }
    }
  }
}
