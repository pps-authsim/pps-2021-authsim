package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.builder.Builder
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.{PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.util.Util.generateRandomString


/**
 * Abstract class for building users, it provides basic method to add policies and check if user credentials are complaint with them.
 * @tparam U
 */
abstract class UserBuilder[U] extends Builder[U]:
  protected var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty
  protected var _userName: String= ""
  protected var _password:String= ""

  /**
   * Method that check if the the provided credential for the user meet the input policy
   *
   * @return true if the the credentials are complaint with the policy or false if they are not
   */
  protected def checkPolicy(): Boolean=
    _credentialPolicies.map(c =>
      StringPolicyChecker(c) check (
        c match
          case _: UserIDPolicy => _userName
          case _: PasswordPolicy => _password
          case _ => ""
        )
    ).contains(false).unary_!
    
  /**
   * Setter for the policy to apply to the credential of a user
   *
   * @param policy      a policy to apply to the credential of the user
   * @return            a UserCostumBuilder where the in the policy field is added the provided value
   */
  def withPolicy(policy:CredentialPolicy) =
    this.builderMethod((policy: CredentialPolicy) => this._credentialPolicies = policy +: this._credentialPolicies)(policy)