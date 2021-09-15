package it.unibo.authsim.library.user.builder
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.CredentialPolicy

trait UserGenerator:
  def of(credentialPolicy: CredentialPolicy): UserGenerator

object UserGenerator:
  def apply(): UserGenerator= new UserGeneratorImpl()

  private class UserGeneratorImpl() extends UserGenerator:
    private var _credentialPolicies: Seq[CredentialPolicy] = Seq.empty

    override def of(credentialPolicy: CredentialPolicy): UserGenerator =
      this._credentialPolicies = credentialPolicy +: this._credentialPolicies
      this