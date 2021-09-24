package it.unibo.authsim.library.user.builder.util

import scala.util.Random
import scala.collection.immutable.Map
/**
 * Object for usefull functions
 */
object RandomStringGenerator:
  /**
   * Method that generate a randomic string composed by letter and numbers
   * @param length    length of the string to be created, default value is 5
   * @return          new randomic string of the given length 
   */
  def generateRandomString(length:Int=5): String=
    Random.alphanumeric.take(length).mkString

  def countDuplicates(numbers: Seq[String]): Int = {
    numbers.toArray.foldLeft(Map.empty[String, Int]) { (map, item) =>
      map + (item -> (map.getOrElse(item, 0) + 1))
    }.count(_._2 > 1)
  }