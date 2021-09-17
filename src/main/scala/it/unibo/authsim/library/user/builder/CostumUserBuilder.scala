package it.unibo.authsim.library.user.builder
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.user.builder.util.Util.generateRandomString

class UserCostumBuilder extends UserBuilder[User]:
  def withName(userName:String):this.type=
    this._userName = userName
    this

  def withPassword(password:String):this.type=
    this._password = password
    this

  def build(): Option[User]=
    if(checkPolicy()) then
      val user= User(_userName, _password)
      Some(user)
    else
      None

