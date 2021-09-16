package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import scala.util.Random
case class UserAutoBuilder(val credentialPolicy:Seq[CredentialPolicy]= Seq.empty): //extends UserBuilder(username, password):
  private def genrateRandomString(length:Int=5): String=
    Random.alphanumeric.filter(_.isDigit).take(length).mkString

  def build(): Option[User]=
    var result1=true
    var result2=true
    if(credentialPolicy.isEmpty) then
      val user: User = User(genrateRandomString(), genrateRandomString())
      Some(user)
    else None
      //for( e <-credentialPolicy)
        //if(e.isInstanceOf[PasswordPolicy])then
          //e.asInstanceOf[PasswordPolicy]
          //result1=StringPolicyChecker(e.asInstanceOf[PasswordPolicy]) check password
        //else if (e.isInstanceOf[UserIDPolicy])then result2=StringPolicyChecker(e.asInstanceOf[UserIDPolicy]) check username
        //None


