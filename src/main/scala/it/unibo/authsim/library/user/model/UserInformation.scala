package it.unibo.authsim.library.user.model

import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.user.model.User

/**
 * Trait that represent a UserInformation
 */
trait UserInformation() extends User:
  def algorithm: Option[CryptographicAlgorithm]

/**
 * Object that represent a UserInformation
 */
object UserInformation:
  
  /**
   * Apply method for User
   * 
   * @param username              Name of the user
   * @param password              Encrypted password of the user
   * @param algorithm     Information about the algorithm used to encrypt the password
   * @return
   */
  def apply(username: String,
            password: String,
            algorithm: Option[CryptographicAlgorithm]) =
            new UserInformationImpl(username,password, algorithm)

  def apply(username: String,
            password: String) =
    new UserInformationImpl(username,password, None)
  /**
   * Case class implementing UserInformation trait
   * @param username            Name of the user
   * @param password            Encrypted password of the user
   * @param algorithm    Information about the algorithm used to encrypt the password
   */
  case class UserInformationImpl(override val username: String,
                                 override val password: String,
                                 algorithm: Option[CryptographicAlgorithm])
                                   extends UserInformation()