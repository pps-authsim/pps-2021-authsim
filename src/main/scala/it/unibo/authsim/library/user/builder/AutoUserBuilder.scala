package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.policy.extractors.CredentialPolicyGenerate.{PasswordGenerate, UserIDGenerate}
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.util.Util.generateRandomString
import it.unibo.authsim.library.user.model.User

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
  def build: User=
    val map=_credentialPolicies.map(c => c match
      case UserIDGenerate(userId) => "userID" -> userId
      case PasswordGenerate(password) => "password" -> password
      case _=> "others" -> ""
    ).toMap
    this._userName=map.getOrElse("userID", generateRandomString())
    this._password=map.getOrElse("password", generateRandomString())
    User(_userName, _password)
    
  /**
   * Method responsible of the generation of a given number of users
   *
   * @param numberOfUsers   number of users to be created it should be a posive value, if not it will be implicitly converted to be so
   * @return                a sequence of the require number of users
   */
  def build(numberOfUsers: Int): Seq[User] = List.fill(numberOfUsers.abs)(build).toSeq