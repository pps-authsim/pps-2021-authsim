package it.unibo.authsim.library.otp.generators

import scala.util.Random

/**
 * ''SeedGenerator'' is a trait used to generate a seed for OTP generation.
 * @tparam T the type of seed
 */
trait SeedGenerator[T]:
  /**
   * @param previousSeed the previous generated seed to set
   * @return a generic seed of session for OTP generation.
   */
  def seed(previousSeed: T): T

object SeedGenerator:
  /**
   * A default implementation of an seed generator.
   */
  implicit val generatorSeed: SeedGenerator[Int] = new SeedGenerator[Int]:
    override def seed(previousSeed: Int): Int =
      val genSeed = Random.between(Int.MinValue, Int.MaxValue)
      if genSeed == previousSeed then genSeed + 1 else genSeed

