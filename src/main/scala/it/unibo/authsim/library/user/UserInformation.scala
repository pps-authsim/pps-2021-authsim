package it.unibo.authsim.library.user

trait UserInformation (saltInformation: SaltInformation,
                       additionalInformation: Map[String, String]) extends User

object UserInformation:
  def apply(username: String,
            password: String,
            saltInformation: SaltInformation,
            additionalInformation: Map[String, String]= Map.empty) =
            new UserInformationImpl(username,password, saltInformation, additionalInformation)

  case class UserInformationImpl(username: String ,
                                    password: String,
                                    saltInformation: SaltInformation,
                                    additionalInformation: Map[String, String]=Map.empty)
                                   extends UserInformation(saltInformation, additionalInformation)

trait SaltInformation (lenght: Option[Int],
                       choicePolicy:Option[String],
                       value: Option[String])

object SaltInformation:
  def apply(lenght: Option[Int] = Option.empty, 
            choicePolicy: Option[String] = Option.empty,
            value: Option[String] = Option.empty): SaltInformation = SaltInformationImpl(lenght,choicePolicy, value)
  
  case class SaltInformationImpl(lenght: Option[Int]=Option.empty, 
                                 choicePolicy: Option[String], 
                                 value: Option[String]) extends SaltInformation(lenght, choicePolicy, value)