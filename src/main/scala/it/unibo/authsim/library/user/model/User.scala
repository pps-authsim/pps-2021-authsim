package it.unibo.authsim.library.user.model

private[model] trait User:
  val username: String
  val password: String

private[model] object User:
  def apply(_username:String, _password:String): User=
    new User:
      override val username:String=_username
      override val password:String=_password