package it.unibo.authsim.library.user.model

/**
 * Trait that represent a user
 */
trait User:
  val username: String
  val password: String

/**
 * Object that represent a user
 */
object User:

  /**
   * Class to implement a simple version of the User trait
   * @param username      username of the user
   * @param password      password to associated with the username
   */
  private case class BasicUser(override val username:String, override val password:String) extends User

  /**
   * Apply method for a user
   *
   * @param username     name of the user
   * @param password     password in clear of the user
   * @return              a BasicUser
   */
  def apply(username:String, password:String): User = BasicUser(username, password)
