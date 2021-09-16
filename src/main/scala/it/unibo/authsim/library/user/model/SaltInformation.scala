package it.unibo.authsim.library.user.model

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