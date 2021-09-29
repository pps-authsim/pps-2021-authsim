package it.unibo.authsim.library.dsl.cryptography.util

object ImplicitConversion:
  private val charset: String = "UTF8" //TODO da sistemare: come gestisco il fatto che qualuno cambi il charset dalla classe che chiama il metodo? Parametro implicito?Boh

  implicit def objectToString[A](inputObject: A): String = inputObject.toString

  implicit def objectToInt [A] (inputObject: A): Int =
    inputObject match {
      case _: A =>
        Integer.valueOf(inputObject.toString).intValue()
      case _=> 0
    }

  implicit def ArrayByteToString(value :Array[Byte]):String =value.toString
  implicit def stringToCharArray(value : String):Array[Char] =value.toCharArray
  implicit def stringToArrayByte(value : String):Array[Byte] =value.getBytes(charset)
