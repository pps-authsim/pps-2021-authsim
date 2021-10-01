package it.unibo.authsim.library.dsl.policy.extractors

import it.unibo.authsim.library.dsl.policy.model.StringPolicies
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

import scala.reflect.ClassTag

/**
 * ''ExtractorCredentialPolicy'' is trait used to extract the actual type of an [[CredentialPolicy credential policy]] instance
 * @tparam T actual type of credential policy
 */
trait ExtractorCredentialPolicy[T: ClassTag]:
  def unapply(credentialPolicy: CredentialPolicy): Option[T] =
    credentialPolicy match {
      case _: T => Some(credentialPolicy.asInstanceOf[T])
      case _ => None
    }

object ExtractorCredentialPolicy:
  /**
   * ''UserIDPolicy'' extracts the [[StringPolicies.UserIDPolicy userID policy]] instance
   *
   */
  object UserIDPolicy extends ExtractorCredentialPolicy[UserIDPolicy]
  /**
   * ''PasswordPolicy'' extracts the [[StringPolicies.PasswordPolicy password policy]] instance
   */
  object PasswordPolicy extends ExtractorCredentialPolicy[PasswordPolicy]
  /**
   * ''OTPPolicy'' extracts the [[StringPolicies.OTPPolicy otp policy]] instance
   */
  object OTPPolicy extends ExtractorCredentialPolicy[OTPPolicy]