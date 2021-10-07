package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.builder.Builder
import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.user.model.UserInformation

import scala.language.postfixOps
import it.unibo.authsim.library.user.builder.UserBuilder

/**
 * Class that represent a builder of UserInformation
 */
class UserInformationBuilder extends Builder[Option[UserInformation]]:
  protected var _algorithm:Option[CryptographicAlgorithm] = None
  protected var _userName: String = ""
  protected var _encryptedPassword:String = ""

  /**
   * Setter for the username of the user
   * 
   * @param name                  UserName to use for the generation of the new userInformation
   * @return                      a UserInformationBuilder where the username field is setted with the provided value
   */
  def withUserName(name:String) : this.type =
    this._userName = name
    this

  /**
   * Setter for the encrypted password of the user
   * 
   * @param encryptedPassword     Password to use for the generation of the new userInformation
   * @return                      a UserInformationBuilder where the username field is setted with the provided value
   */
  def withPassword(encryptedPassword:String):this.type =
    this._encryptedPassword = encryptedPassword
    this

  /**
   * Setter for the cryptoInformation of the user
   * 
   * @param cryptoInformation     CryptoInformation to use for the generation of the new userInformation
   * @returna                     UserInformationBuilder where the username field is setted with the provided value
   */
  def withAlgorithm(algorithm:CryptographicAlgorithm) : this.type =
    val _algorithm=Some(algorithm)
    this
    
  /**
   * Method that create an optional of userInformation if the credential for the user are provided or an optional of None if they are not
   *
   * @return      an optional of UserInformation
   */
  def build: Option[UserInformation]=
    if(!this._userName.isEmpty && !this._encryptedPassword.isEmpty ) then
      val user= UserInformation(_userName, _encryptedPassword, _algorithm)
      Some(user)
    else None