package it.unibo.authsim.library.user
import scala.language.postfixOps
import it.unibo.authsim.library.policy.builders.stringpolicy.OTPPolicyBuilder
import it.unibo.authsim.library.policy.builders.stringpolicy.{PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.policy.model.StringPolicies.{CredentialPolicy, OTPPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.UserCostumBuilder
import it.unibo.authsim.library.user.builder.UserBuilder
import it.unibo.authsim.library.user.builder.UserAutoBuilder
import it.unibo.authsim.library.user.model.User
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.policy.checkers.StringPolicyChecker


class UserBuilderTest extends AnyWordSpec with should.Matchers{

  private val userIDPolicy: CredentialPolicy = UserIDPolicyBuilder() minimumLength 5 build
  private val passwordPolicy: CredentialPolicy = PasswordPolicyBuilder() minimumUpperChars 3 minimumLength 8 build
  private val name: String= "name1"
  private val shortName: String= "na"
  private val password: String= "password1"

  private val costumUserBuilder1 = UserCostumBuilder() withName(name) withPassword(password)
  private val costumUser1 :Option[User]= costumUserBuilder1.build

  private val costumUserBuilder2 =  UserCostumBuilder() withName(name) withPassword(password) withPolicy(userIDPolicy)
  private val costumUser2:Option[User] = costumUserBuilder2.build

  private val costumUserBuilder3 =  UserCostumBuilder() withName(shortName) withPassword(password) withPolicy(userIDPolicy)
  private val costumUser3:Option[User] = costumUserBuilder3.build

  private val autoUserBuilder1= UserAutoBuilder() withPolicy(userIDPolicy)
  private val autoUser1:User = autoUserBuilder1.build

  private val autoUserBuilder2 = UserAutoBuilder() withPolicy(userIDPolicy) withPolicy(passwordPolicy)
  private val autoUser2:User = autoUserBuilder2.build

  s"A user created with name '${name}' and password '{$password}'" should {
    "have name" in{
      costumUser1.get.username should be (name)
    }
    "and its password should be" in {
      costumUser1.get.password should be (password)
    }
  }

  s"If a user created with a '${userIDPolicy.getClass}' then user credentials" should  {
    s"be complaint with the policy'${userIDPolicy}'" in{
      assert(StringPolicyChecker(userIDPolicy) check (costumUser1.get.username))
    }
  }

  "If a user chose a credential not complaint with the given policy" should {
    "not be able to create a user" in{
      costumUser3 should be (None)
    }
  }

  "A user auto-generated given some users' credentials " should {
    s"have a name complaint with the '${userIDPolicy.getClass}'" in{
      assert(StringPolicyChecker(userIDPolicy) check (autoUser1.username))
    }
    s"and a password complaint with the '${passwordPolicy.getClass}'" in {
      assert(StringPolicyChecker(userIDPolicy) check (autoUser1.password))
    }
  }
}