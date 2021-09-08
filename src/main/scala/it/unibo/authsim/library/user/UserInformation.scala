package it.unibo.authsim.library.user

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

trait SaltInformation (lenght: Option[Int], choicePolicy:Option[String], value: Option[String])

object SaltInformation:
  def apply(lenght: Option[Int], choicePolicy: Option[String],value: Option[String]): SaltInformation = SaltInformationImpl(lenght,choicePolicy, value)
  case class SaltInformationImpl(lenght: Option[Int], choicePolicy: Option[String], value: Option[String]) extends SaltInformation(lenght, choicePolicy, value)