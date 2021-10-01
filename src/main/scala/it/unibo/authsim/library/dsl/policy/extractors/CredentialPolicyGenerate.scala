package it.unibo.authsim.library.dsl.policy.extractors

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

import scala.reflect.ClassTag

/**
 * ''CredentialPolicyGenerate'' is a trait used to generate an random string of the actual credential policy instance
 * @tparam T actual type of credential policy
 */
trait CredentialPolicyGenerate[T: ClassTag]:
  def unapply(credentialPolicy: CredentialPolicy): Option[String] =
    credentialPolicy match {
      case _: T => Some(credentialPolicy.generate)
      case _ => None
    }


object CredentialPolicyGenerate:
  /**
   * ''UserIDGenerate'' generates a random userID
   */
  object UserIDGenerate extends CredentialPolicyGenerate[UserIDPolicy]
  /**
   * ''PasswordGenerate'' generates a random password
   */
  object PasswordGenerate extends CredentialPolicyGenerate[PasswordPolicy]
  /**
   * ''OTPGenerate'' generates a random One Time Password
   */
  object OTPGenerate extends CredentialPolicyGenerate[OTPPolicy]