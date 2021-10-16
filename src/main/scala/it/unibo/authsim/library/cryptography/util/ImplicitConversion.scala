package it.unibo.authsim.library.cryptography.util

/**
 * Utility object that provides some implicit conversions.
 */
object ImplicitConversion:
  /**
   * Variable representing the charset to be used in encoding operations.
   */
  private val charset: String = "UTF8"

  /**
   * Utility object to perform implicit conversions.
   */
  object ImplicitConversionToBuiltinType:
    import ImplicitToArray._
    /**
     * Method that converts a generic value into a string.
     * 
     * @param value : generic value to be converted
     * @tparam A : type of the object
     * @return  a string representing the input value
     */
    implicit def genericToString[A](value: A): String = value.toString

    /**
     * Method that converts a generic value into an int.
     * 
     * @param value : generic value to be converted
     * @tparam A : type of the object
     * @return : the int value of the value if a conversion is possible, or 0 in any other case
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
     * Method that converts a string into a byte.
     *
     * @param value : string value to be converted
     * @return : a byte representing the input string
     */
    implicit def stringToByte(value : String):Byte = Byte(value)


  /**
   * Object to provide implicit conversion to a sequence.
   */
  object ImplicitToSeq:
    /**
     * Method that converts a generic array into a sequence.
     *
     * @param array : input array to convert
     * @tparam A : generic parameter
     * @return : a sequence of the given generic parameter
     */
    implicit def arrayToSeq[A](array: Array[A]):Seq[A] = array.toSeq

    /**
     * Method that converts a list into a sequence.
     *
     * @param list : input list to convert
     * @tparam A : generic parameter
     * @return : a sequence of the given generic parameter
     */
    implicit def listToSeq[A](list:List[A]):Seq[A]= list.toSeq

  /**
   * Object to provide implicit conversion to a list.
   */
  object ImplicitToList:

    /**
     * Method that converts a generic array into a list.
     *
     * @param list : input list to convert
     * @tparam A : generic parameter
     * @return : an array of the given generic parameter
     */
    implicit def arrayToList[A](list: Array[A]):List[A] = list.toList

    /**
     * Method that converts a generic sequence into a list.
     *
     * @param seq : input sequence to convert
     * @tparam A : generic parameter
     * @return : a list of the given generic parameter
     */
    implicit def seqToList[A](seq:Seq[A]):List[A]= seq.toList

  /**
   * Object to provide implicit conversion to an array.
   */
  object ImplicitToArray:
    import ImplicitConversionToBuiltinType._
    import scala.reflect.ClassTag

    /**
     * Method that converts a generic list into an array.
     *
     * @param list : input list to convert
     * @tparam A : generic parameter
     * @return : an array of the given generic parameter
     */
    def listToArray[A](list: List[A])(implicit ev: ClassTag[A]):Array[A] = list.toArray

    /**
     * Method that converts a generic sequence into an array.
     *
     * @param seq : input sequence to convert
     * @tparam A : Generic parameter
     * @return : an array of the given generic parameter
     */
    implicit def seqToArray[A](seq: Seq[A])(implicit ev: ClassTag[A]):Array[A] = seq.toArray

    /**
     * Method that converts a string into a byte array.
     *
     * @param value : string value to be converted
     * @return : a byte array representing the input string
     */
    implicit def stringToByteArray(value : String):Array[Byte] =value.getBytes(charset)

    /**
     * Method that converts a generic value into a byte array.
     *
     * @param value : generic value to be converted
     * @tparam A : type of the object
     * @return : a byte array representing the input value
     */
    implicit def genericToArrayByte[A](value :A):Array[Byte] = stringToByteArray(value)

    /**
     * Method that converts a string into a char array.
     *
     * @param value : string value to be converted
     * @return : a char array representing the input string
     */
    implicit def stringToCharArray(value : String):Array[Char] =value.toCharArray
