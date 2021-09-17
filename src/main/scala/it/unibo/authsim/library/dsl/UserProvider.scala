package it.unibo.authsim.library.dsl

import it.unibo.authsim.library.user.model.UserInformation

trait UserProvider :
  def userInformations(): List[UserInformation]

object UserProvider:
  def apply(): UserProvider= new UserProvider:
    private var _userInformations: List[UserInformation] = List.empty
    override def userInformations(): List[UserInformation]= _userInformations
    //TODO discuti con Alex riguardo al setter: per me dovrebbe fornire un modo per agggiungere utenti esattamente come succede per i builder