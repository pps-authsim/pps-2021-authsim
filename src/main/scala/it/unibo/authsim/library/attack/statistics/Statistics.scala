package it.unibo.authsim.library.attack.statistics

import it.unibo.authsim.library.user.model.User

import scala.concurrent.duration.Duration

/**
 * A Statistics memorizes a set of Users, the number of attempts and the elapsed time of an attack.
 * @param successfulBreaches A Set of Users whose passwords have been discovered by an attack.
 * @param attempts The number of attempts made by an attack.
 * @param elapsedTime The time elapsed from the start of the attack to its end.
 */
class Statistics(val successfulBreaches: Set[User], val attempts: Long, val elapsedTime: Duration, val timedOut: Boolean):
  /**
   * Adds two statistics by the union of the User Sets, the addition of the attempt number and the addition of the elapsed time.
   * @param other The other Statistics.
   * @return A new Statistics as the result of the additions and union.
   */
  def +(other: Statistics): Statistics = new Statistics(this.successfulBreaches ++ other.successfulBreaches, this.attempts + other.attempts, this.elapsedTime + other.elapsedTime, this.timedOut || other.timedOut)

/**
 * Companion object for Statistics in which defines some utility methods.
 */
object Statistics:
  /**
   *
   * @return A 'zero' Statistics with an empty Set, 0 attempts and a zero duration.
   */
  def zero: Statistics = new Statistics(Set(), attempts = 0, elapsedTime = Duration.Zero, timedOut = false)

  /**
   * @return An empty Statistics which has timed out.
   */
  def timedOut: Statistics = new Statistics(Set(), attempts = 0, elapsedTime = Duration.Zero, timedOut = true)

  /**
   * @param successfulBreaches The set of successfully breached users.
   * @return A zero-statistics with only the set of breached users.
   */
  def onlyBreaches(successfulBreaches: Set[User]) = new Statistics(successfulBreaches, attempts = 0, elapsedTime = Duration.Zero, timedOut = false)

  /**
   * @param attempts The number of attempts.
   * @return A statistics with only the number of attempts-
   */
  def onlyAttempts(attempts: Long) = new Statistics(Set(), attempts, elapsedTime = Duration.Zero, timedOut = false)

  /**
   * @param elapsedTime The elapsed time Duration.
   * @return A statistics with only the elapsed time.
   */
  def onlyElapsedTime(elapsedTime: Duration) = new Statistics(Set(), attempts = 0, elapsedTime, timedOut = false)
