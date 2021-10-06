package it.unibo.authsim.client.app.components.persistence

import scala.util.Try

trait UserRepository:

  def saveUsers(users: Seq[UserEntity]): Try[Unit]
  
  def resetUsers(): Try[Unit]

  def retrieveUser(username: String, password: String): Try[UserEntity]
  
  def retrieveAllUsers(): Try[Seq[UserEntity]]