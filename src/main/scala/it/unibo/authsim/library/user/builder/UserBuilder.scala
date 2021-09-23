package it.unibo.authsim.library.user.builder

import  it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.util.RandomStringGenerator.generateRandomString
//ANche qui non sono troppo sicura sull'iso
abstract class UserBuilder[U]:
  protected var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty
  protected var _userName: String=generateRandomString()
  protected var _password:String=generateRandomString()

  /**
   * Method that check if the the provided credential for the user meet the input policy
   * 
   * @return true if the the credentials are complaint with the policy or false if they are not
   */
  protected def checkPolicy(): Boolean=
    _credentialPolicies.filter(e=> (e.isInstanceOf[PasswordPolicy]|| e.isInstanceOf[UserIDPolicy])).map(e=>
      if(e.isInstanceOf[PasswordPolicy]) then
        StringPolicyChecker(e.asInstanceOf[PasswordPolicy]) check _password
      else
        StringPolicyChecker(e.asInstanceOf[UserIDPolicy]) check _userName
    ).contains(false).unary_!
    
  /**
   * Setter for the policy to apply to the credential of a user
   *
   * @param policy      a policy to apply to the credential of the user
   * @return            a UserCostumBuilder where the in the policy field is added the provided value
   */
  def withPolicy(policy:CredentialPolicy):this.type =
    this._credentialPolicies = policy +: this._credentialPolicies
    this