package it.unibo.authsim.components.persistence

trait UserRepository:

  def saveUsers(users: Seq[UserEntity]): Unit
  
  def resetUsers(): Unit

  def retrieveUser(username: String, password: String): Option[UserEntity]