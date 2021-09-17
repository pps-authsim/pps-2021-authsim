package it.unibo.authsim.library.user.builder

import  it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.Builder
import it.unibo.authsim.library.user.builder.util.Util.generateRandomString

abstract class UserBuilder[U] extends Builder[U]:
  protected var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty
  protected var _userName: String=generateRandomString()
  protected var _password:String=generateRandomString()

  protected def checkPolicy(): Boolean=
    _credentialPolicies.filter(e=> (e.isInstanceOf[PasswordPolicy]|| e.isInstanceOf[UserIDPolicy])).map(e=>
      if(e.isInstanceOf[PasswordPolicy]) then
        StringPolicyChecker(e.asInstanceOf[PasswordPolicy]) check _password
      else
        StringPolicyChecker(e.asInstanceOf[UserIDPolicy]) check _userName
    ).contains(false).unary_!

  def withPolicy(policy:CredentialPolicy):this.type =
    this._credentialPolicies = policy +: this._credentialPolicies
    this