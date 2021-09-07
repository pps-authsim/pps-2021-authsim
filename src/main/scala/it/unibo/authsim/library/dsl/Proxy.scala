package it.unibo.authsim.library.dsl

trait Proxy :
  def getUserInformations(): List[UserInformation]

object Proxy:
  def apply(): Proxy= ProxyImpl()
  case class ProxyImpl() extends Proxy:
    private var _userInformations: List[UserInformation] = List.empty
    override def getUserInformations() = _userInformations


trait SaltInformation:
  def lenght: Option[Int]
  def choicePolicy:Option[String]
  def value: Option[String]

object SaltInformation:
  def apply(lenght: Option[Int], choicePolicy: Option[String],value: Option[String]): SaltInformation = SaltInformationImpl(lenght,choicePolicy, value)
  case class SaltInformationImpl(lenght: Option[Int], choicePolicy: Option[String], value: Option[String]) extends SaltInformation

trait UserInformation:
  def username: String
  def password: String
  def saltInformation: SaltInformation
  def additionalInformation: Map[String, String]

object UserInformation:
  def apply(username: String, password: String, saltInformation: SaltInformation, additionalInformation: Map[String, String]): UserInformation = UserInformationImpl(username, password, saltInformation, additionalInformation)
  case class UserInformationImpl(username: String, password: String, saltInformation: SaltInformation, additionalInformation: Map[String, String]) extends UserInformation

