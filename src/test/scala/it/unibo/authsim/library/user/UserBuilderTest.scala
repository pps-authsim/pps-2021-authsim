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
  private val userIDPolicy: CredentialPolicy = UserIDPolicyBuilder() minimumLength 3 build
  private val passwordPolicy: CredentialPolicy = PasswordPolicyBuilder() minimumUpperChars 3 minimumLength 8 build
  private val name1: String= "name1"
  private val name2: String= "name2"
  private val password1: String= "password1"
  private val password2: String= "password2"

  private val costumUserBuilder1 = UserCostumBuilder() withName(name1) withPassword(password2)
  private val costumUser1 :Option[User]= costumUserBuilder1.build()

  private val costumUserBuilder2 = UserCostumBuilder() withName(name2) withPassword(password1) withPolicy(userIDPolicy)
  private val costumUser2:Option[User] = costumUserBuilder1.build()

  private val autoUserBuilder1= UserAutoBuilder() withPolicy(userIDPolicy)
  private val autoUser1:User = autoUserBuilder1.asInstanceOf[UserAutoBuilder].build()

  private val autoUserBuilder2= UserAutoBuilder() withPolicy(userIDPolicy) withPolicy(passwordPolicy)
  private val autoUser2:User = autoUserBuilder2.asInstanceOf[UserAutoBuilder].build()

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
