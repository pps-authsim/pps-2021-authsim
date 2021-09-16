package it.unibo.authsim.library.user.builder

import scala.util.Random

object CredentialsUtils {
  def generateRandomString(length:Int=5): String=
    Random.alphanumeric.filter(_.isLetterOrDigit).take(length).mkString
}
