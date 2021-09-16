package it.unibo.authsim.library.user
import scala.language.postfixOps
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.UserCostumBuilder
import it.unibo.authsim.library.user.builder.UserBuilder
import it.unibo.authsim.library.user.builder.UserAutoBuilder
import it.unibo.authsim.library.user.model.User
import org.scalatest.wordspec.AnyWordSpec

class UserBuilderTest extends AnyWordSpec{
  /*
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
*/
  private val userIDPolicy: CredentialPolicy = UserIDPolicyBuilder() minimumLength 3 build
  private val passwordPolicy: CredentialPolicy = PasswordPolicyBuilder() minimumUpperChars 3 minimumLength 8 build
  private val name1: String= "name1"
  private val name2: String= "name2"
  private val password1: String= "password1"
  private val password2: String= "password2"

  private val costumUserBuilder1: UserBuilder[User] = UserCostumBuilder() withName(name1) withPassword(password2)
  private val costumUser1 = costumUserBuilder1.build()

  private val costumUserBuilder2: UserBuilder[User] = UserCostumBuilder() withName(name2) withPassword(password1) withPolicy(userIDPolicy)
  private val costumUser2 = costumUserBuilder1.build()

  private val autoUserBuilder1: UserBuilder[User]= UserAutoBuilder() withPolicy(userIDPolicy)
  private val autoUser1 = autoUserBuilder1.build()

  private val autoUserBuilder2: UserBuilder[User]= UserAutoBuilder() withPolicy(userIDPolicy) withPolicy(passwordPolicy)
  private val autoUser2 = autoUserBuilder2.build()

  println(costumUser1)
  println(costumUser2)
  println(autoUser1)
  println(autoUser2)
  "A user created with name $name1 and password $password1" should {
    "have name" in{
      //costumUser1.username should be name1
    }
  }
}
