package it.unibo.authsim.library.dsl.policy.extractors

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

import scala.reflect.ClassTag

trait CredentialPolicyGenerate[T: ClassTag]:
  def unapply(credentialPolicy: CredentialPolicy): Option[String] =
    credentialPolicy match {
      case _: T => Some(credentialPolicy.generate)
      case _ => None
    }


object CredentialPolicyGenerate:
  object UserIDGenerate extends CredentialPolicyGenerate[UserIDPolicy]
  object PasswordGenerate extends CredentialPolicyGenerate[PasswordPolicy]
  object OTPGenerate extends CredentialPolicyGenerate[OTPPolicy]