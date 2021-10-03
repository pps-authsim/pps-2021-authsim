package it.unibo.authsim.library.dsl.cryptography.utility

import it.unibo.authsim.library.dsl.cryptography.utility.ImplicitConversionChecker.isConversionAvailable

object ImplicitConversionChecker:
  private object ConversionUnavailable extends (Any => Nothing):
    def apply(x: Any) = sys.error("No conversion")

  private def noConversion: Any => Nothing = ConversionUnavailable

  def isConversionAvailable[A,B]()(implicit f: A => B = noConversion): Boolean =
    (f ne ConversionUnavailable)

  def convert[A,B](a: A)(implicit f: A => B = noConversion): Option[B] =
    if (f ne ConversionUnavailable)then
      Some(f(a))
    else None

object App extends App:
  println(isConversionAvailable[String, Int]())