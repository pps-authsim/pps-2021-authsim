package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

import scala.collection.mutable.ListBuffer
import scala.util.Random

trait PolicyAutoBuilder[T]:
  def generate: T

object PolicyAutoBuilder:
  implicit val stringPolicyAutoBuilder: StringPolicy => PolicyAutoBuilder[String] =
    (policy: StringPolicy) =>
      new PolicyAutoBuilder[String]:
        override def generate: String =
          val generatedString: ListBuffer[Char] = ListBuffer.empty

          policy match
            case otpPolicy: OTPPolicy =>
              println("Add numbers")
              generatedString ++= otpPolicy.alphabet.randomDigits.take(Random.between(otpPolicy.minimumLength, otpPolicy.maximumLength + 1))
    
            case sPolicy: StringPolicy with RestrictStringPolicy with MoreRestrictStringPolicy =>
              println(s"Policy -> length (${sPolicy.minimumLength}, ${sPolicy.maximumLength})")
              if sPolicy.minimumSymbols > 0
              then
                println(s"Add ${sPolicy.minimumSymbols} symbols")
                generatedString ++= sPolicy.alphabet.randomSymbols.take(sPolicy.minimumSymbols)
    
              if sPolicy.minimumUpperChars > 0
              then
                println(s"Add ${sPolicy.minimumUpperChars} uppers")
                generatedString ++= sPolicy.alphabet.randomUppers.take(sPolicy.minimumUpperChars)
    
              if sPolicy.minimumNumbers > 0
              then
                println(s"Add ${sPolicy.minimumNumbers} digits")
                generatedString ++= sPolicy.alphabet.randomDigits.take(sPolicy.minimumNumbers)
    
              if sPolicy.minimumLowerChars > 0
              then
                println(s"Add ${sPolicy.minimumLowerChars} lowers")
                generatedString ++= sPolicy.alphabet.randomLowers.take(sPolicy.minimumLowerChars)
    
              val minBound: Int = if generatedString.length >= sPolicy.minimumLength then 0 else sPolicy.minimumLength - generatedString.length
              val maxBound = (sPolicy.maximumLength - generatedString.length) + 1
              val numChars: Int = Random.between(minBound, maxBound)
              println(s"Add more missing chars: random ($minBound, $maxBound) => add $numChars chars")
              generatedString ++= sPolicy.alphabet.randomAlphanumericsymbols.take(numChars)
    
          println(s"Generated $generatedString")
          Random.shuffle(generatedString).mkString