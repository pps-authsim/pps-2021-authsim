package it.unibo.authsim.library.dsl

import it.unibo.authsim.library.user.model.UserInformation

/**
 * Trait for a UserProvider: it provides a list of UserInformation with whom the client can interact;
 */
trait UserProvider :
  /**
   * Getter for a list of user information.
   *
   * @return    a list of UserInformation
   */
  def userInformations(): List[UserInformation]