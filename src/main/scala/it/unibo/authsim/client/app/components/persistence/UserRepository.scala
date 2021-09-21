package it.unibo.authsim.client.app.components.persistence

trait UserRepository:

  def saveUsers(users: Seq[UserEntity]): Unit
  
  def resetUsers(): Unit

  def retrieveUser(username: String, password: String): Option[UserEntity]