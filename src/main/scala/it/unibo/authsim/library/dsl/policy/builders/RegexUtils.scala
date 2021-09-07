package it.unibo.authsim.library.dsl.policy.builders

import scala.util.matching.Regex

object RegexUtils:
  def minimalLength: Regex = "".r
  def minimumLength(number: Int): Regex = "".r
  def rangeLength(min: Int, max: Int): Regex = "".r
  def minimumLowerCharacters(number: Int): Regex = "".r
  def minimumUpperCharacters(number: Int): Regex = "".r
  def minimumSymbols(number: Int): Regex = "".r
  def minimumNumbers(number: Int): Regex = "".r