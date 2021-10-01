package it.unibo.authsim.library.dsl.policy.extractors

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*

import scala.reflect.ClassTag

trait ExtractorCredentialPolicy[T: ClassTag]:
  def unapply(credentialPolicy: CredentialPolicy): Option[T] =
    credentialPolicy match {
      case _: T => Some(credentialPolicy.asInstanceOf[T])
      case _ => None
    }

object ExtractorCredentialPolicy:
  object UserIDPolicy extends ExtractorCredentialPolicy[UserIDPolicy]
  object PasswordPolicy extends ExtractorCredentialPolicy[PasswordPolicy]
  object OTPPolicy extends ExtractorCredentialPolicy[OTPPolicy]