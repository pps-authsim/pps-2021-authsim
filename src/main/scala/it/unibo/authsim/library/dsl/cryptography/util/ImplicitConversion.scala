package it.unibo.authsim.library.dsl.cryptography.util

object ImplicitConversion:
  implicit def objectToString[A](inputObject: A): String = inputObject.toString
  implicit def objectToInt [A] (inputObject: A): Int =
    inputObject match {
      case _: String => inputObject.toInt
      case _=> 0
    }