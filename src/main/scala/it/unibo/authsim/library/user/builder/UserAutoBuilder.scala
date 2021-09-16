package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import scala.util.Random

case class UserAutoBuilder(val credentialPolicy:Seq[CredentialPolicy]= Seq.empty): //extends UserBuilder(username, password):
  private var name:String=""
  private var password:String=""
  private def genrateRandomString(length:Int=5): String=
    Random.alphanumeric.filter(_.isLetterOrDigit).take(length).mkString

  def build(): User=
    if(credentialPolicy.isEmpty) then
      User(genrateRandomString(), genrateRandomString())
    else
      credentialPolicy.filter(e=> (e.isInstanceOf[PasswordPolicy]|| e.isInstanceOf[UserIDPolicy])).map(e=>
        if(e.isInstanceOf[PasswordPolicy]) then
          password=e.asInstanceOf[PasswordPolicy].generate
        else if (e.isInstanceOf[UserIDPolicy]) then
          name=e.asInstanceOf[UserIDPolicy].generate
        )//TO-DO if no polcy is passed it should get the default one
      User(name, password)



