package it.unibo.authsim.library.dsl.policy.alphabet

import scala.util.matching.Regex

trait RegexAlphabet extends Alphabet:
  def minimalLength: Regex = ("[" + this.alphanumericsymbols.mkString + "]+").r
  def minimumLength(number: Int): Regex = ("[" + this.alphanumericsymbols.mkString + "]{" + number + ",}").r
  def rangeLength(min: Int, max: Int): Regex = ("[" + this.alphanumericsymbols.mkString + "]{" + min + "," + max + "}").r
  def minimumLowerCharacters(number: Int): Regex = ("^(?=(?:.*[" + this.lowers.mkString + "]){" + number + ",}).+$").r
  def minimumUpperCharacters(number: Int): Regex = ("^(?=(?:.*[" + this.uppers.mkString + "]){" + number + ",}).+$").r
  def minimumSymbols(number: Int): Regex = ("^(?=(?:.*[" + this.symbols.mkString + "]){" + number + ",}).+$").r
  def minimumNumbers(number: Int): Regex = ("^(?=(?:.*[" + this.digits.mkString + "]){" + number + ",}).+$").r
  def onlyNumbers: Regex = ("^[" + this.digits.mkString + "]*$").r
