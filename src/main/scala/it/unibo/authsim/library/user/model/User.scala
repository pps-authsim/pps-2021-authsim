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
   * Apply method for  a user
   * 
   * @param _username     name of the user
   * @param _password     password in clear of the user
   * @return              a new user
   */
  def apply(_username:String, _password:String): User=
    new User:
      override val username:String=_username
      override val password:String=_password