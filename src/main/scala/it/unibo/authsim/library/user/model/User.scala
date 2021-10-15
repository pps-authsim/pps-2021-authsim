package it.unibo.authsim.library.user.model

/**
 * Trait that represents a user.
 */
trait User:
  def username: String
  def password: String

/**
 * Companion object for the trait User.
 */
object User:

  /**
   * Class that implements a simple version of the User trait.
   * 
   * @param username : username of the user
   * @param password : password in clear of the user
   */
  private case class BasicUser(override val username:String, override val password:String) extends User

  /**
   * Apply method for a user.
   *
   * @param username : username of the user
   * @param password : password in clear of the user
   * @return : a BasicUser
   */
  def apply(username:String, password:String): User = BasicUser(username, password)
