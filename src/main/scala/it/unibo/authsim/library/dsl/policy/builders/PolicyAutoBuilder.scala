package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.policy.checkers.PolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

import java.util.regex.Matcher
import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
 * A ''PolicyAutoBuilder'' is a trait that is used to generate a type T given a policy
 * @tparam T the type to generate
 */
trait PolicyAutoBuilder[T]:
  /**
   * Generate a random value of type T based a given policy
   * @return generated value of type T
   */
  def generate: T

object PolicyAutoBuilder:
  /**
   *  Implicitly converts a [[StringPolicy string policy]] into an instance of [[PolicyAutoBuilder policy auto builder]] of type String
   */
  implicit val stringPolicyAutoBuilder: StringPolicy => PolicyAutoBuilder[String] =
    (policy: StringPolicy) =>
      new PolicyAutoBuilder[String]:
        override def generate: String =
          val generatedString: ListBuffer[Char] = ListBuffer.empty
          val maxDefault: Int => Int = (min: Int) => min + 10

          val randomInt: (Int, Int, Option[Int]) => Int = (actualLength: Int, min: Int, max: Option[Int]) =>
            val minBound: Int = if actualLength >= min then 0 else min - actualLength
            val maxBound = (max.getOrElse(maxDefault(min)) - actualLength) + 1
            val numChars: Int = Random.between(minBound, maxBound)
            numChars

          policy match
            case otpPolicy: OTPPolicy =>
              generatedString ++= otpPolicy.alphabet.randomDigits.take(Random.between(otpPolicy.minimumLength, otpPolicy.maximumLength.getOrElse(maxDefault(otpPolicy.minimumLength)) + 1))

            case mRStringPolicy: StringPolicy with RestrictStringPolicy with MoreRestrictStringPolicy =>
              if mRStringPolicy.minimumSymbols.isDefined
              then generatedString ++= mRStringPolicy.alphabet.randomSymbols.take(mRStringPolicy.minimumSymbols.get)
    
              if mRStringPolicy.minimumUpperChars.isDefined
              then generatedString ++= mRStringPolicy.alphabet.randomUppers.take(mRStringPolicy.minimumUpperChars.get)
    
              if mRStringPolicy.minimumNumbers.isDefined
              then generatedString ++= mRStringPolicy.alphabet.randomDigits.take(mRStringPolicy.minimumNumbers.get)
    
              if mRStringPolicy.minimumLowerChars.isDefined
              then generatedString ++= mRStringPolicy.alphabet.randomLowers.take(mRStringPolicy.minimumLowerChars.get)

              val numChars: Int =  randomInt(generatedString.length, mRStringPolicy.minimumLength, mRStringPolicy.maximumLength)
              generatedString ++= mRStringPolicy.alphabet.randomAlphanumericsymbols.take(numChars)

            case rStringPolicy: StringPolicy with RestrictStringPolicy =>
              val numChars: Int = randomInt(generatedString.length, rStringPolicy.minimumLength, rStringPolicy.maximumLength)
              generatedString ++= rStringPolicy.alphabet.randomAlphanumericsymbols.take(numChars)

            case stringPolicy: StringPolicy =>
              val numChars: Int = randomInt(generatedString.length, 1, Some(30))
              generatedString ++= stringPolicy.alphabet.randomAlphanumericsymbols.take(numChars)

          Random.shuffle(generatedString).mkString