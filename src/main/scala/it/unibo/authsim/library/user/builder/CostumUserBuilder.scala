package it.unibo.authsim.library.user.builder
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.user.builder.util.Util.generateRandomString

//TODO controlla se con i metodi del builder si può parlare di setter
/**
 * Class that represent a costum builder for a user
 */
class UserCostumBuilder extends UserBuilder[User]:
  /**
   * Setter for the username of the user
   * 
   * @param userName    userName to use for the generation of the new user
   * @return            a UserCostumBuilder where the username field is setted with the provided value
   */
  def withName(userName:String):this.type=
    this._userName = userName
    this
  /**
   * Setter for the username of the User
   *
   * @param password
   * @return            a UserCostumBuilder where the username field is setted with the provided value
   */
  def withPassword(password:String):this.type=
    this._password = password
    this

  /**
   * Method that create a user if the credential provided meet the input policy or an optional of None if they does not
   * 
   * @return      an optional of User
   */
  def build(): Option[User]=
    if(checkPolicy()) then
      val user= User(_userName, _password)
      Some(user)
    else
      None

