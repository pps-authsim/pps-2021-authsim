package it.unibo.authsim.library.user.builder.util

import scala.util.Random

object Util {
  def generateRandomString(length:Int=5): String=
    Random.alphanumeric.filter(_.isLetterOrDigit).take(length).mkString
}
