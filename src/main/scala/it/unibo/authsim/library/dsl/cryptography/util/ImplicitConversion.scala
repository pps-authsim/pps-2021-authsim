package it.unibo.authsim.library.dsl.cryptography.util

object ImplicitConversion:
  /**
   * Variable representing the charset to be used in encoding operations
   */
  private val charset: String = "UTF8"

  /**
   * Utility object to perform implicit conversions
   */
  object ImplicitConversion:
    import ImplicitToArray._
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
     * Method responsible of performing an implicit conversion from a string to a byte
     *
     * @param value           string value to be converted
     * @return                a byte representing the input string
     */
    implicit def stringToByte(value : String):Byte = Byte(value)


  /**
   * Object to provide implicit conversion to a sequence
   */
  object ImplicitToSeq:
    /**
     * Method that convert an array to a list
     *
     * @param array   input array to convert
     * @tparam A      Generic parameter
     * @return        a sequence of the given generic parameter
     */
    implicit def arrayToSeq[A](array: Array[A]):Seq[A] = array.toSeq

    /**
     * Method that convert an array to a list
     *
     * @param list    input list to convert
     * @tparam A      Generic parameter
     * @return        a sequence of the given generic parameter
     */
    implicit def listToSeq[A](list:List[A]):Seq[A]= list.toSeq

  /**
   * Object to provide implicit conversion to a list
   */
  object ImplicitToList:

    /**
     * Method that convert an array to a list
     *
     * @param list    input list to convert
     * @tparam A      Generic parameter
     * @return        an array of the given generic parameter
     */
    implicit def arrayToList[A](list: Array[A]):List[A] = list.toList

    /**
     * Method that convert a sequence to a list
     *
     * @param seq     input sequence to convert
     * @tparam A      Generic parameter
     * @return        a list of the given generic parameter
     */
    implicit def seqToList[A](seq:Seq[A]):List[A]= seq.toList

  /**
   * Object to provide implicit conversion to an array
   */
  object ImplicitToArray:
    import ImplicitConversion._
    import scala.reflect.ClassTag

    /**
     * Method that convert a list into an array
     *
     * @param list    input list to convert
     * @tparam A      Generic parameter
     * @return        an array of the given generic parameter
     */
    def listToArray[T](list: List[T])(implicit ev: ClassTag[T]):Array[T] = list.toArray

    /**
     * Method that convert a sequence into an array
     *
     * @param seq     input sequence to convert
     * @tparam A      Generic parameter
     * @return        an array of the given generic parameter
     */
    implicit def seqToArray[T](seq: Seq[T])(implicit ev: ClassTag[T]):Array[T] = seq.toArray

    /**
     * Method responsible of performing an implicit conversion from a string to a byte array
     *
     * @param value           string value to be converted
     * @return                a byte array representing the input string
     */
    implicit def stringToByteArray(value : String):Array[Byte] =value.getBytes(charset)

    /**
     * Method responsible of performing an implicit conversion from a generic to a byte array
     *
     * @param value           generic value to be converted
     * @tparam A              type of the object
     * @return                a byte array representing the input value
     */
    implicit def genericToArrayByte[A](value :A):Array[Byte] = stringToByteArray(value)

    /**
     * Method responsible of performing an implicit conversion from a string to a char array
     *
     * @param value           string value to be converted
     * @return                a char array representing the input string
     */
    implicit def stringToCharArray(value : String):Array[Char] =value.toCharArray
