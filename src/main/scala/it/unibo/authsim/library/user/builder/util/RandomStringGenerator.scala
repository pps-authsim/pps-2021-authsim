package it.unibo.authsim.library.user.builder.util

import scala.util.Random

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