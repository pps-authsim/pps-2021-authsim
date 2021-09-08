package it.unibo.authsim.library.user

import it.unibo.authsim.library.dsl.SaltInformation

trait UserInformation (saltInformation: SaltInformation, additionalInformation: Map[String, String]) extends User

object UserInformation:
  def apply(username: String,
            password: String,
            saltInformation: SaltInformation,
            additionalInformation: Map[String, String]) =
            new UserInformationImpl(username,password, saltInformation, additionalInformation)

  case class UserInformationImpl(username: String ,
                                    password: String,
                                    saltInformation: SaltInformation,
                                    additionalInformation: Map[String, String])
                                   extends UserInformation(saltInformation, additionalInformation)