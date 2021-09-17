package it.unibo.authsim.library.dsl

import it.unibo.authsim.library.user.model.UserInformation
//UserProvider
trait Proxy :
  def getUserInformations(): List[UserInformation]

object Proxy:
  def apply(): Proxy= ProxyImpl()
  case class ProxyImpl() extends Proxy:
    private var _userInformations: List[UserInformation] = List.empty
    override def getUserInformations() = _userInformations