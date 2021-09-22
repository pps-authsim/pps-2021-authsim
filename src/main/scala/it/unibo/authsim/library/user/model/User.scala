package it.unibo.authsim.library.user.model

trait User:
  val username: String
  val password: String

object User:
  def apply(_username:String, _password:String): User=
    new User:
      override val username:String=_username
      override val password:String=_password