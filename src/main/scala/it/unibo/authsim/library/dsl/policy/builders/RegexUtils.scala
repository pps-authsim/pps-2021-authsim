package it.unibo.authsim.library.dsl.policy.builders

import scala.util.matching.Regex

object RegexUtils:
  def minimalLength: Regex = ".+".r
  def minimumLength(number: Int): Regex = (".{" + number + ",}").r
  def rangeLength(min: Int, max: Int): Regex = (".{" + min + "," + max + "}").r
  def minimumLowerCharacters(number: Int): Regex = ("^(?=(?:.*[a-z]){" + number + ",}).+$").r
  def minimumUpperCharacters(number: Int): Regex = ("^(?=(?:.*[A-Z]){" + number + ",}).+$").r
  def minimumSymbols(number: Int): Regex = ("^(?=(?:.*[!@#$%^&*]){" + number + ",}).+$").r
  def minimumNumbers(number: Int): Regex = ("^(?=(?:.*[0-9]){" + number + ",}).+$").r
  def onlyNumbers: Regex = "^[\\d]*$".r