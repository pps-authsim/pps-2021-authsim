package it.unibo.authsim.library.user
import scala.language.postfixOps
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.UserBuilder.UserBuilder
import it.unibo.authsim.library.user.builder.UserBuilder2
import it.unibo.authsim.library.user.builder.UserAutoBuilder
import it.unibo.authsim.library.user.model.User
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
  var user3: UserAutoBuilder= UserAutoBuilder()
  var user4: UserAutoBuilder= UserAutoBuilder(Seq(userIDPolicy))
  var u3:User=user3.build()
  var u4:User=user4.build()
  println(u3)
  println(u4)

}
