package it.unibo.authsim.library.dsl.cryptography.util

object ImplicitConversion:
  private val charset: String = "UTF8"

  implicit def genericToString[A](inputObject: A): String = inputObject.toString

  implicit def genericToInt [A] (inputObject: A): Int =
    inputObject match {
      case _: String =>
        if(inputObject.toString.toIntOption.!=(None)) then
          Integer.valueOf(inputObject.toString).intValue()
        else
          0
      case _=> 0
    }

  implicit def genericToArrayByte[A](value :A):Array[Byte] = stringToArrayByte(genericToString(value))
  implicit def stringToCharArray(value : String):Array[Char] =value.toCharArray
  implicit def stringToByte(value : String):Byte = Byte(value)
  implicit def stringToArrayByte(value : String):Array[Byte] =value.getBytes(charset)
