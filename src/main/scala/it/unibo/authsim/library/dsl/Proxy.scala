package it.unibo.authsim.library.dsl

import it.unibo.authsim.library.user.UserInformation

trait Proxy :
  def getUserInformations(): List[UserInformation]

object Proxy:
  def apply(): Proxy= ProxyImpl()
  case class ProxyImpl() extends Proxy:
    private var _userInformations: List[UserInformation] = List.empty
    override def getUserInformations() = _userInformations

trait SaltInformation (lenght: Option[Int], choicePolicy:Option[String], value: Option[String])

object SaltInformation:
  def apply(lenght: Option[Int], choicePolicy: Option[String],value: Option[String]): SaltInformation = SaltInformationImpl(lenght,choicePolicy, value)
  case class SaltInformationImpl(lenght: Option[Int], choicePolicy: Option[String], value: Option[String]) extends SaltInformation(lenght, choicePolicy, value)