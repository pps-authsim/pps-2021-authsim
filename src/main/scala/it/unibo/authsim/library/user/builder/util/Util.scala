package it.unibo.authsim.library.user.builder.util

/**
 * Object that provides usefull functions
 */
object Util:
  /**
   * Method that generates a randomic string composed by letter and numbers
   *
   * @param length    length of the string to be created, default value is 5
   * @return          new randomic string of the given length
   */
  def generateRandomString(length:Int=5): String=
    import org.apache.commons.lang3.RandomStringUtils
    RandomStringUtils.randomAlphanumeric(length)
    
  /**
   * Method that generates a randomic string composed by letter and numbers 
   * of a random length in a range
   *
   * @param minVal    min length of the string
   * @param maxVal    maxlenght of the string
   * @return          new randomic string of random length in the given range
   */
  def generateRandomString(minVal: Int, maxVal:Int): String=
    import scala.util.Random
    Random.alphanumeric.filter(_ isLetterOrDigit).take(Random between(minVal,maxVal)) mkString

  /**
   * Method that counts the occurence of the elements in a sequence
   * 
   * @param list      sequence of elements
   * @return          the number of elments that occures multiple times in the input sequence
   */
  def countDuplicates[A](list: Seq[A]): Int =
    import scala.collection.immutable.Map
    list.foldLeft(Map.empty[A, Int]) { (map, item) =>
      map + (item -> (map.getOrElse(item, 0) + 1))
    }.count(_._2 > 1)