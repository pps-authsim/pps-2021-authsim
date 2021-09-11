package it.unibo.authsim.library.dsl.policy.auto.generator

import it.unibo.authsim.library.dsl.policy.AlphabetPolicy.*
import it.unibo.authsim.library.dsl.policy.builders.StringPolicy
import it.unibo.authsim.library.dsl.policy.builders.StringPolicy.*

import scala.collection.mutable.ListBuffer
import scala.language.postfixOps
import scala.util.Random

object StringPolicyAutoBuilder:

  def apply(policy: StringPolicy.Builder): PolicyAutoBuilder[String] = new PolicyAutoBuilder[String]:
    override def generate: String =
      println(s"Policy -> length (${policy.getMinimumLength}, ${policy.getMaximumLength})")
      val generatedString: ListBuffer[Char] = ListBuffer.empty

      policy match
        case builder: OnlyCharsBuilder =>

          if builder.getMinimumSymbols > 0
          then
            println(s"Add ${builder.getMinimumSymbols} symbols")
            generatedString ++= Random.symbols.take(builder.getMinimumSymbols)

          if builder.getMinimumUpperChars > 0
          then
            println(s"Add ${builder.getMinimumUpperChars} uppers")
            generatedString ++= Random.uppers.take(builder.getMinimumUpperChars)

          if builder.getMinimumNumbers > 0
          then
            println(s"Add ${builder.getMinimumNumbers} digits")
            generatedString ++= Random.digits.take(builder.getMinimumNumbers)

          if builder.getMinimumLowerChars > 0
          then
            println(s"Add ${builder.getMinimumLowerChars} lowers")
            generatedString ++= Random.lowers.take(builder.getMinimumLowerChars)

          val minBound: Int = if generatedString.length >= policy.getMinimumLength then 0 else policy.getMinimumLength - generatedString.length
          val maxBound = (policy.getMaximumLength - generatedString.length) + 1
          val numChars: Int = Random.between(minBound, maxBound)
          println(s"Add more missing chars: random ($minBound, $maxBound) => add $numChars chars")
          generatedString ++= Random.alphanumericsymbols.take(numChars)

        case _: OTPPolicy =>
          println("Add numbers")
          generatedString ++= Random.digits.take(Random.between(policy.getMinimumLength, policy.getMaximumLength + 1))

      println(s"Generated $generatedString")
      Random.shuffle(generatedString).mkString

  def apply(policies: Seq[StringPolicy.Builder]): PolicyAutoBuilder[Seq[String]] = new PolicyAutoBuilder[Seq[String]]:
    override def generate: Seq[String] = policies map { StringPolicyAutoBuilder(_) generate }
