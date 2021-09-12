package it.unibo.authsim.library.dsl.policy.alphabet

import scala.util.matching.Regex
import it.unibo.authsim.library.dsl.policy.alphabet.RegexAlphabet

trait Alphabet:
  def lowers: Seq[Char] = Seq.empty
  def uppers: Seq[Char] = Seq.empty
  def digits: Seq[Char] = Seq.empty
  def symbols: Seq[Char] = Seq.empty
  def alphanumericsymbols: Seq[Char] = lowers ++ uppers ++ digits ++ symbols
