package it.unibo.authsim.library.user.builder.util

/**
 * Object to provide usefull function
 */
object Util:
  /**
   * Method that generate a randomic string composed by letter and numbers
   *
   * @param length    length of the string to be created, default value is 5
   * @return          new randomic string of the given length
   */
  def generateRandomString(length:Int=5): String=
    import org.apache.commons.lang3.RandomStringUtils
    RandomStringUtils.randomAlphanumeric(length)

  /**
   * Method to count the occurence of the elements of Sequence
   * @param list      sequence of elements
   * @return          the number of elments that occures multiple times in the input sequence
   */
  def countDuplicates[A](list: Seq[A]): Int =
    import scala.collection.immutable.Map
    list.foldLeft(Map.empty[A, Int]) { (map, item) =>
      map + (item -> (map.getOrElse(item, 0) + 1))
    }.count(_._2 > 1)

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
   * Object to provide implicit conversion to an array
   */
  object ImplicitToArray:
    import scala.reflect.ClassTag

    /**
     * Method that convert a list into an array
     *
     * @param list    input list to convert
     * @tparam A      Generic parameter
     * @return        an array of the given generic parameter
     */
    def listToArray[T](list: List[T])(implicit ev: ClassTag[T]) = list.toArray

    /**
     * Method that convert a sequence into an array
     *
     * @param seq     input sequence to convert
     * @tparam A      Generic parameter
     * @return        an array of the given generic parameter
     */
    def seqToArray[T](seq: Seq[T])(implicit ev: ClassTag[T]) = seq.toArray