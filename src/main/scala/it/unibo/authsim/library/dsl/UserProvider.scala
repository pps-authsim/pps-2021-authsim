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

/**
 * Object for a UserProvider
 */
object UserProvider:
  /**
   * Apply method for the object
   *
   * @return a UserProvider
   */
  def apply(): UserProvider= new UserProvider:
    //NB Non sto a scrivere altro perchè non so quanto durerà sta variabile
    /**
     * Variable that represent a representation of a userInformations provided
     */
    private var _userInformations: List[UserInformation] = List.empty

    /**
     * Getter method for the userInformation 
     * 
     * @return a List of UserInformation
     */
    override def userInformations(): List[UserInformation]= _userInformations
