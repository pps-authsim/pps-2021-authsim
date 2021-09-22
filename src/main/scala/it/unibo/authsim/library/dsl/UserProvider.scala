package it.unibo.authsim.library.dsl

import it.unibo.authsim.library.user.model.UserInformation

trait UserProvider :
  def userInformations(): List[UserInformation]

object UserProvider:
  def apply(): UserProvider= new UserProvider:
    private var _userInformations: List[UserInformation] = List.empty
    override def userInformations(): List[UserInformation]= _userInformations
