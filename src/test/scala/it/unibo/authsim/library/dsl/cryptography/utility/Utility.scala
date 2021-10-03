package it.unibo.authsim.library.dsl.cryptography.utility

object ImplicitConversionChecker:
  private object NoConversion extends (Any => Nothing):
    def apply(x: Any) = sys.error("No conversion")
  
  private def noConversion: Any => Nothing = NoConversion

  def canConvert[A,B]()(implicit f: A => B = noConversion) =
    (f ne NoConversion)

  def optConvert[A,B](a: A)(implicit f: A => B = noConversion): Option[B] =
    if (f ne NoConversion)then Some(f(a)) else None
