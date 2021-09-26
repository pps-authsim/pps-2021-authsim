package it.unibo.authsim.library.user.builder

import it.unibo.authsim.library.dsl.policy.defaults.PolicyDefault
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.UserIDPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.CredentialPolicy
import it.unibo.authsim.library.user.model.{CryptoInformation, UserInformation}
import scala.language.postfixOps
import it.unibo.authsim.library.user.builder.UserBuilder

/**
 * Class that represent a builder of UserInformation
 */
class UserInformationBuilder extends OptionalBuilder[UserInformation]:
  /*
    TODO : algorithmPolicy: si assume che se la policy non è stata settata allora è salvata in chiaro:
     di default viene salvata in chiaro, altrimenti deve essere chiamato il metodo per aggiornare il campo
     Per il momento viene usata una mock class attendendo il momento in cui non verrà deciso come rappresentare
     effettivamente questo dato.
     L'unica cosa su cui siamo concordi è che Crypto abbia un qualche tipo che estende da policy
 */
  private val mock= PolicyDefault()
  private val algorithmPolicy: Policy = mock.simple

  protected var _cryptoInformation:CryptoInformation = CryptoInformation(algorithmPolicy)
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
  def withAlgorithmPolicy(cryptoInformation:CryptoInformation) : this.type =
    this._cryptoInformation = cryptoInformation
    this
    
  /**
   * Method that create an optional of userInformation if the credential for the user are provided or an optional of None if they are not
   *
   * @return      an optional of UserInformation
   */
  override def build():Option[UserInformation]=
    if(!this._userName.isEmpty && !this._encryptedPassword.isEmpty ) then
      val user= UserInformation(_userName, _encryptedPassword, _cryptoInformation)
      Some(user)
    else None