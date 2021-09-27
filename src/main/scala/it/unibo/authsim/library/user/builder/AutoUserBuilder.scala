package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
//Non sono sicurissima che qui sia sensata una classe o se sarebbe meglio un object

/**
 * Class that represent an automatic builder for a user
 */
class UserAutoBuilder extends UserBuilder[User]{
  //TODO override properties when Marica creates default policies

  /**
   * Method that create a user whose credentials meet the provided policies
   * 
   * @return  a User which credentials are complaint with the input policies
   */
  def build: User=
    _credentialPolicies.filter(e=> (e.isInstanceOf[PasswordPolicy]|| e.isInstanceOf[UserIDPolicy]))
      .map(e=>
        if(e.isInstanceOf[PasswordPolicy]) then
          this._password= e.generate
        else
          this._userName= e.generate)
    User(_userName, _password)
}
