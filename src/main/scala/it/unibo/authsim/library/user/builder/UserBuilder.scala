package it.unibo.authsim.library.user.builder
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.PasswordPolicyBuilder
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*
import scala.language.postfixOps

//trait UserBuilder(username: String, password:String ) extends UserGenerator

object UserBuilder:
  def apply(username: String, password:String) = new UserBuilder(username, password, credentialPolicy=Seq.empty)
  def apply(username: String, password:String, credentialPolicy: Seq[CredentialPolicy]) = new UserBuilder(username, password, credentialPolicy)
  case class UserBuilder(val username:String, val password:String, val credentialPolicy:Seq[CredentialPolicy]): //extends UserBuilder(username, password):
    //_credentialPolicies=credentialPolicy
    private def checkPolicy(): Boolean=
      var result1=true
      var result2=true
      for( e <-credentialPolicy)
       if(e.isInstanceOf[PasswordPolicy])then result1=StringPolicyChecker(e.asInstanceOf[PasswordPolicy]) check password
       else if (e.isInstanceOf[UserIDPolicy])then result2=StringPolicyChecker(e.asInstanceOf[UserIDPolicy]) check username
      result1&&result2

    def build(): Option[User]=
      if(checkPolicy()) then
        val user: User = User(username, password)
        Some(user)
      else
        None


case class UserBuilder(val username:String, val password:String, val credentialPolicy:Seq[CredentialPolicy]): //extends UserBuilder(username, password):
  //_credentialPolicies=credentialPolicy
  private def checkPolicy(): Boolean=
    var result1=true
    var result2=true
    for( e <-credentialPolicy)
      if(e.isInstanceOf[PasswordPolicy])then result1=StringPolicyChecker(e.asInstanceOf[PasswordPolicy]) check password
      else if (e.isInstanceOf[UserIDPolicy])then result2=StringPolicyChecker(e.asInstanceOf[UserIDPolicy]) check username
    result1&&result2

  def build(): Option[User]=
    if(checkPolicy()) then
      val user: User = User(username, password)
      Some(user)
    else
      None


case class UserBuilder2(val username:String, val password:String, val credentialPolicy:Seq[CredentialPolicy]= Seq.empty): //extends UserBuilder(username, password):
  //_credentialPolicies=credentialPolicy
  private def checkPolicy(): Boolean=
  //questo punto esclamativo davanti fa un po' schifo
    !credentialPolicy.filter(e=> (e.isInstanceOf[PasswordPolicy]|| e.isInstanceOf[UserIDPolicy])).map(e=>
      if(e.isInstanceOf[PasswordPolicy]) then
        StringPolicyChecker(e.asInstanceOf[PasswordPolicy]) check password
      else if (e.isInstanceOf[UserIDPolicy]) then
        StringPolicyChecker(e.asInstanceOf[UserIDPolicy]) check username
    ).contains(false)

  def build(): Option[User]=
    if(checkPolicy()) then
      val user: User = User(username, password)
      Some(user)
    else
      None


