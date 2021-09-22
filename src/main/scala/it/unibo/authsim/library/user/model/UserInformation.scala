package it.unibo.authsim.library.user.model

import it.unibo.authsim.library.user.model.User

trait UserInformation (saltInformation: CryptoInformation,
                       additionalInformation: Map[String, String]) extends User

object UserInformation:
  def apply(username: String,
            password: String,
            saltInformation: CryptoInformation,
            additionalInformation: Map[String, String]= Map.empty) =
            new UserInformationImpl(username,password, saltInformation, additionalInformation)

  //non sono sicura che la password debba essere una val, forse sarebbe meglio una var
  case class UserInformationImpl(override val username: String,
                                 override val password: String,
                                 saltInformation: CryptoInformation,
                                 additionalInformation: Map[String, String]=Map.empty)
                                   extends UserInformation(saltInformation, additionalInformation)