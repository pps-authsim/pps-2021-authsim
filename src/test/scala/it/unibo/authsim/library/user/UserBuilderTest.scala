package it.unibo.authsim.library.user
import scala.language.postfixOps
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.UserBuilder.UserBuilder
import it.unibo.authsim.library.user.builder.UserBuilder2
import org.scalatest.wordspec.AnyWordSpec

class UserBuilderTest extends AnyWordSpec{
  private val userIDPolicy: UserIDPolicy = UserIDPolicyBuilder() minimumLength 1 build
  private val passwordPolicy: CredentialPolicy = PasswordPolicyBuilder() minimumLength 1 build
  var userBuilder:UserBuilder= UserBuilder("name", "password",  Seq(passwordPolicy, userIDPolicy))
  var user= userBuilder.build()
  var userBuilder1:UserBuilder2= UserBuilder2("name", "password")
  var user1= userBuilder1.build()
  println(user)
  println(user1)
}
