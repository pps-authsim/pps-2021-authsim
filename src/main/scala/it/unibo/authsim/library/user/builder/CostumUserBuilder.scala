package it.unibo.authsim.library.user.builder
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.user.builder.util.Util.generateRandomString

/**
 * Class that represents a costum builder for a user.
 */
class UserCostumBuilder extends UserBuilder[Option[User]]:

  /**
   * Setter for the username of the user.
   * 
   * @param userName : userName used for the generation of the new user
   * @return : a UserCostumBuilder where the username field is set with the provided value
   */
  def withName(userName:String) = this.builderMethod((userName: String) => this._userName = userName)(userName)

  /**
   * Setter for the username of the User.
   *
   * @param password : password used for the generation of the new user
   * @return : a UserCostumBuilder where the password field is set with the provided value
   */
  def withPassword(password:String) = this.builderMethod((password: String) => this._password = password)(password)


  /**
   * Method that creates an optional of user if the credentials provided meet the input policy or an None if they do not.
   * 
   * @return : an optional of User
   */
  def build: Option[User]=
    if(checkPolicy()) then
      val user= User(_userName, _password)
      Some(user)
    else
      None