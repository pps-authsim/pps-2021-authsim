package it.unibo.authsim.library.user.builder
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.user.builder.CredentialsUtils
import it.unibo.authsim.library.user.builder.CredentialsUtils.generateRandomString

abstract class UserBuilder[U] extends Builder[U]:
  protected var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty
  protected var _userName: String=""
  protected var _password:String=""
  protected def checkPolicy(): Boolean=
      !_credentialPolicies.filter(e=> (e.isInstanceOf[PasswordPolicy]|| e.isInstanceOf[UserIDPolicy])).map(e=>
        if(e.isInstanceOf[PasswordPolicy]) then
          StringPolicyChecker(e.asInstanceOf[PasswordPolicy]) check _password
        else if (e.isInstanceOf[UserIDPolicy]) then
          StringPolicyChecker(e.asInstanceOf[UserIDPolicy]) check _userName
      ).contains(false)

  def withPolicy(policy:CredentialPolicy)=
    this._credentialPolicies = policy +: this._credentialPolicies
    this

  def build(): Option[U] | U

class UserCostumBuilder extends UserBuilder[User]:
  def withName(userName:String)=
    this._userName = userName
    this

  def withPassword(password:String)=
    this._password = password
    this

  override def build(): Option[User]=
    if(this.checkPolicy()) then
      val user= User(_userName, _password)
      Some(user)
    else
      None

class UserAutoBuilder extends UserBuilder[User]:

  override def build(): User=
    if(_credentialPolicies.isEmpty) then
      User(generateRandomString(), generateRandomString())
    else
      _credentialPolicies.filter(e=> (e.isInstanceOf[PasswordPolicy]|| e.isInstanceOf[UserIDPolicy])).map(e=>
        if(e.isInstanceOf[PasswordPolicy]) then
          _password=e.asInstanceOf[PasswordPolicy].generate
        else if (e.isInstanceOf[UserIDPolicy]) then
          _userName=e.asInstanceOf[UserIDPolicy].generate
      )//TO-DO if no polcy is passed it should get the default one
      User(_userName, _password)