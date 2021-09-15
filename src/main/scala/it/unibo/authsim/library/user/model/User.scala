package it.unibo.authsim.library.user.model

trait User:
  def username: String
  def password: String

object User:
  def apply(username:String, password:String)= new UserImpl(username,password)
  case class UserImpl(val username:String, val password:String) extends User
