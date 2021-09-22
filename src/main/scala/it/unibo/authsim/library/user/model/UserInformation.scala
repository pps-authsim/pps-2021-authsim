package it.unibo.authsim.library.user.model

import it.unibo.authsim.library.user.model.User

/**
 * Trait that represent a UserInformation
 */
trait UserInformation (cryptoInformation: CryptoInformation) extends User

/**
 * Object that represent a UserInformation
 */
object UserInformation:
  /**
   * Apply method for User
   * @param username            Name of the user
   * @param password            Encrypted password of the user
   * @param cryptoInformation     Information about the algorithm used to encrypt the password
   * @return
   */
  def apply(username: String,
            password: String,
            cryptoInformation: CryptoInformation) =
            new UserInformationImpl(username,password, cryptoInformation)

  //non sono sicura che la password debba essere una val, forse sarebbe meglio una var
  case class UserInformationImpl(override val username: String,
                                 override val password: String,
                                 cryptoInformation: CryptoInformation)
                                   extends UserInformation(cryptoInformation)