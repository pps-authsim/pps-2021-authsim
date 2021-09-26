package it.unibo.authsim.library.user.builder

import  it.unibo.authsim.library.dsl.policy.defaults.PolicyDefault
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.UserIDPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.CredentialPolicy
import it.unibo.authsim.library.user.model.{CryptoInformation, UserInformation}
import scala.language.postfixOps

trait OptionalBuilder[U]:
  def build():Option[U]

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

  def withUserName(name:String) : this.type =
    this._userName=name
    this
    
  def withPassword(encryptedPassword:String):this.type =
    this._encryptedPassword=encryptedPassword
    this
    
  def withAlgorithmPolicy(cryptoInformation:CryptoInformation):this.type =
    this._cryptoInformation=cryptoInformation
    this
    
  override def build():Option[UserInformation]=
    if(!this._userName.isEmpty && !this._encryptedPassword.isEmpty ) then
      val user= UserInformation(_userName, _encryptedPassword, _cryptoInformation)
      Some(user)
    else None