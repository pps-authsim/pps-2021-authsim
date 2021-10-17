package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.builder.Builder
import it.unibo.authsim.library.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.user.model.UserInformation

import scala.language.postfixOps
import it.unibo.authsim.library.user.builder.UserBuilder

/**
 * Class that represents a builder for a UserInformation.
 */
class UserInformationBuilder extends Builder[Option[UserInformation]]:
  protected var _algorithm:Option[CryptographicAlgorithm] = None
  protected var _userName: String = ""
  protected var _encryptedPassword:String = ""

  /**
   * Setter for the username of the user.
   * 
   * @param name : userName to use for the generation of the new userInformation
   * @return : a UserInformationBuilder where the username field is set with the provided value
   */
  def withUserName(name:String) : this.type =
    this._userName = name
    this

  /**
   * Setter for the encrypted password of the user.
   * 
   * @param encryptedPassword : password to use for the generation of the new UserInformation
   * @return : a UserInformationBuilder where the username field is set with the provided value
   */
  def withPassword(encryptedPassword:String):this.type =
    this._encryptedPassword = encryptedPassword
    this

  /**
   * Setter for the algorithm used the encrypt the password.
   * 
   * @param algorithm : an optional of the algorithm used to encrypt the password, or None if the password is not encrypted
   * @return : a UserInformationBuilder where the algorithm field is set with the provided value
   */
  def withAlgorithm(algorithm:CryptographicAlgorithm) : this.type =
    this.builderMethod[CryptographicAlgorithm](algorithm => this._algorithm = Some(algorithm))(algorithm)
    
  /**
   * Method that creates an optional of a UserInformation if the credentials for the user are provided, or None if they are not.
   *
   * @return : an optional of UserInformation
   */
  def build: Option[UserInformation]=
    if(!this._userName.isEmpty && !this._encryptedPassword.isEmpty ) then
      val user= UserInformation(_userName, _encryptedPassword, _algorithm)
      Some(user)
    else None