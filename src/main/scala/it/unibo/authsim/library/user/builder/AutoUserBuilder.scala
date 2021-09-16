package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User

class UserAutoBuilder extends UserBuilder[User]{

  def build(): User=
    _credentialPolicies.filter(e=> (e.isInstanceOf[PasswordPolicy]|| e.isInstanceOf[UserIDPolicy]))
      .map(e=>
        if(e.isInstanceOf[PasswordPolicy]) then
          this._password= e.asInstanceOf[PasswordPolicy].generate
        else if (e.isInstanceOf[UserIDPolicy]) then
          this._userName= e.asInstanceOf[UserIDPolicy].generate)
    User(_userName, _password)


}
