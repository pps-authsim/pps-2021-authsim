package it.unibo.authsim.library.user.model

import it.unibo.authsim.library.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.user.model.User

/**
 * Trait that represents a UserInformation,
 */
trait UserInformation() extends User:
  /**
   * Getter for the algorithm used to encrypt the password,
   *
   * @return : an optional of the algorithm used to encrypt the password, or none if the password was not encrypted
   */
  def algorithm: Option[CryptographicAlgorithm]

/**
 * Companion object for the UserInformation trait.
 */
object UserInformation:
  
  /**
   * Apply method for the UserInformation object.
   * 
   * @param username : username of the user
   * @param password : encrypted password of the user
   * @param algorithm : algorithm used to encrypt the password
   * @return : a BasicUserInformation
   */
  def apply(username: String,
            password: String,
            algorithm: Option[CryptographicAlgorithm]) =
            new BasicUserInformation(username,password, algorithm)

  /**
   * Apply method for the UserInformation object.
   *
   * @param username : username of the user
   * @param password : encrypted password of the user
   * @return : a BasicUserInformation where algorithm field is filled with the default value (None)
   */
  def apply(username: String,
            password: String) =
    new BasicUserInformation(username,password, None)

  /**
   * Case class implementing UserInformation trait.
   * 
   * @param username : name of the user
   * @param password : encrypted password of the user
   * @param algorithm : an optional of the algorithm used to encrypt the password, or none if the password is not encrypted
   */

  case class BasicUserInformation(override val username: String,
                                  override val password: String,
                                  algorithm: Option[CryptographicAlgorithm])
                                   extends UserInformation()