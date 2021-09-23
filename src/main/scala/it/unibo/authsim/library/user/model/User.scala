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
   * @param name     name of the user
   * @param watchword     password in clear of the user
   * @return              a new user
   */
  def apply(name:String, watchword:String): User=
    new User:
      override val username:String=name
      override val password:String=watchword