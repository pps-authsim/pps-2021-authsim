package it.unibo.authsim.client.app.components.persistence

/**
 * Exception indicating error in database operations
 * @param message error message
 */
case class PersistenceException(message: String) extends Exception(message)