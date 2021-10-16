package it.unibo.authsim.client.app.components.persistence

import scala.util.Try

/**
 * Repository responsible for holding and accessing users
 */
trait UserRepository:

  /**
   * Persist given users
   * @param users users to be persisted 
   * @return Try of the operation
   */
  def saveUsers(users: Seq[UserEntity]): Try[Unit]

  /**
   * Deleted all of the currently stored users
   * @return Try of the operation
   */
  def resetUsers(): Try[Unit]

  /**
   * Retrieves a user by username and password
   * @param username username
   * @param password password
   * @return Try of the operation
   */
  def retrieveUser(username: String, password: String): Try[UserEntity]

  /**
   * Retrives all of the currently stored users
   * @return Try of the operation
   */
  def retrieveAllUsers(): Try[Seq[UserEntity]]