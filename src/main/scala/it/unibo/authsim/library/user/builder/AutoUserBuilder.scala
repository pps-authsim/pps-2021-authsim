package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User

/**
 * Class that represent an automatic builder for a user
 */
class UserAutoBuilder extends UserBuilder[User]:

  /**
   * Method that create a user whose credentials meet the provided policies
   * 
   * @return  a User which credentials are complaint with the input policies
   */
  def build(): User=
    _credentialPolicies.map(c =>
      c match
        case _: UserIDPolicy => this._userName = c.generate
        case _: PasswordPolicy => this._password = c.generate
        case _ => ""
    )
    User(_userName, _password)
    
  /**
   * Method responsible of the generation of a given number of users
   *
   * @param numberOfUsers   number of users to be created it should be a posive value, if not it will be implitly converted to be so
   * @return                a sequence of the require number of users
   */
  def build(numberOfUsers: Int): Seq[User] =
    List.fill(numberOfUsers.abs)(build()).toSeq