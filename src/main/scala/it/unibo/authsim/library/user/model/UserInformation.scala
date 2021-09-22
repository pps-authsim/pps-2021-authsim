package it.unibo.authsim.library.user.model

import it.unibo.authsim.library.user.model.User

/**
 * Trait that represent a UserInformation
 */
trait UserInformation (saltInformation: CryptoInformation) extends User

/**
 * Object that represent a UserInformation
 */
object UserInformation:
  def apply(username: String,
            password: String,
            saltInformation: CryptoInformation) =
            new UserInformationImpl(username,password, saltInformation)

  //non sono sicura che la password debba essere una val, forse sarebbe meglio una var
  case class UserInformationImpl(override val username: String,
                                 override val password: String,
                                 saltInformation: CryptoInformation)
                                   extends UserInformation(saltInformation)