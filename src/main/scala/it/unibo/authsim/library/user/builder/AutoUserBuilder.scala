package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.user.builder.util.Util.generateRandomString
import scala.language.postfixOps
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
    this._userName=this._credentialPolicies.find(c=> c.isInstanceOf[UserIDPolicy]).map(c=>c.generate).getOrElse(generateRandomString())
    this._password=this._credentialPolicies.find(c=> c.isInstanceOf[PasswordPolicy]).map(c=>c.generate).getOrElse(generateRandomString())
    User(_userName, _password)
    
  /**
   * Method responsible of the generation of a given number of users
   *
   * @param numberOfUsers   number of users to be created it should be a posive value, if not it will be implitly converted to be so
   * @return                a sequence of the require number of users
   */
  def build(numberOfUsers: Int): Seq[User] =
    List.fill(numberOfUsers.abs)(build()).toSeq

  private def checkCredentials():Unit=
    if(this._userName.isEmpty) then (UserIDPolicyBuilder() minimumLength 5 build).generate
    else if (this._password.isEmpty) then (PasswordPolicyBuilder() minimumLength 5 build).generate