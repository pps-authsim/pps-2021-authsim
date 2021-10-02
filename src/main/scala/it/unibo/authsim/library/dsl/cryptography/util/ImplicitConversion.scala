package it.unibo.authsim.library.dsl.cryptography.util

/**
 * Utility object to perform implicit conversions
 */
object ImplicitConversion:
  /**
   * Variable representing the charset to be used in encoding operations
   */
  private val charset: String = "UTF8"

  /**
   * Method responsible of performing an implicit conversion from a generic to a string
   * @param value           generic value to be converted
   * @tparam A              type of the object
   * @return                a string representing the input value
   */
  implicit def genericToString[A](value: A): String = value.toString

  /**
   * Method responsible of performing an implicit conversion from a generic to an int
   * @param value           generic value to be converted
   * @tparam A              type of the object
   * @return                the int value of the value if a conversion is possible, or 0 in any other case
   */
  implicit def genericToInt [A] (value: A): Int =
    value match {
      case _: String =>
        if(value.toString.toIntOption.!=(None)) then
          Integer.valueOf(value.toString).intValue()
        else
          0
      case _=> 0
    }

  /**
   * Method responsible of performing an implicit conversion from a generic to a byte array
   *
   * @param value           generic value to be converted
   * @tparam A              type of the object
   * @return                a byte array representing the input value
   */
  implicit def genericToArrayByte[A](value :A):Array[Byte] = stringToArrayByte(genericToString(value))

  /**
   * Method responsible of performing an implicit conversion from a string to a char array
   *
   * @param value           string value to be converted
   * @return                a char array representing the input string
   */
  implicit def stringToCharArray(value : String):Array[Char] =value.toCharArray

  /**
   * Method responsible of performing an implicit conversion from a string to a byte
   *
   * @param value           string value to be converted
   * @return                a byte representing the input string
   */
  implicit def stringToByte(value : String):Byte = Byte(value)

  /**
   * Method responsible of performing an implicit conversion from a string to a byte array
   *
   * @param value           string value to be converted
   * @return                a byte array representing the input string
   */
  implicit def stringToArrayByte(value : String):Array[Byte] =value.getBytes(charset)
