package it.unibo.authsim.library.user.builder
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.PasswordPolicyBuilder
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*
import scala.language.postfixOps

trait UserBuilder //extends Builder[User]

object UserBuilder:
  def apply(): UserBuilder = new UserBuilderImpl();
  //def apply(policy:Policy): UserBuilder = new UserBuilderImpl(policy)
  //conviene fare un apply dove passo come policy di default passwordPolicy e userIdPolicy a true?
  //questo oggetto userbuilder deve prendere password, nome e policy? policy le passiamo una ad una o come set di Policy?

  private class UserBuilderImpl() extends UserBuilder:

    private var passwordPolicy: PasswordPolicy =null
    private var userIDPolicy: UserIDPolicy=null

    //queste due sono davvero necessarie?
    private var credentialPolicy: CredentialPolicy=null
    private var policy: Policy=null
    private var name=""
    private var password=""
    private def checkPolicy(): Boolean=
      //da sistemare una volta che capisco da dove prendo le policy e come
      //immagino dovrò usare un policy checker
      passwordPolicy = PasswordPolicyBuilder() minimumLength 5 maximumLength 20 build;
      StringPolicyChecker(passwordPolicy) check password


    def build(): Option[User]=
      if(checkPolicy()) then
        val user: User = User(name, password)
        Some(user)
      else
        None
