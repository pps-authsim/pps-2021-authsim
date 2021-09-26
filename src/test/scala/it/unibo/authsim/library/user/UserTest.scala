package it.unibo.authsim.library.user
import scala.language.postfixOps
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{OTPPolicyBuilder, PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, OTPPolicy, PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.UserCostumBuilder
import it.unibo.authsim.library.user.builder.UserBuilder
import it.unibo.authsim.library.user.builder.UserAutoBuilder
import it.unibo.authsim.library.user.model.User
import org.scalatest.matchers.should
import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.user.builder.util.Util

class UserTest extends AnyWordSpec with should.Matchers{
  private val min: Int=2
  private val max: Int=5

  private val userIDPolicy: CredentialPolicy = UserIDPolicyBuilder() minimumLength max build
  private val passwordPolicy: CredentialPolicy = PasswordPolicyBuilder() minimumUpperChars min minimumLength max build
  private val name: String= Util.generateRandomString()
  private val shortName: String= Util.generateRandomString(min)
  private val password: String= Util.generateRandomString()

  private val costumUserBuilder1:UserCostumBuilder = UserCostumBuilder() withName(name) withPassword(password) withPolicy(userIDPolicy) withPolicy(passwordPolicy)
  private val costumUser1 :Option[User]= costumUserBuilder1.build()

  private val costumUserBuilder2:UserCostumBuilder =  UserCostumBuilder() withName(shortName) withPassword(password) withPolicy(userIDPolicy)
  private val costumUser2:Option[User] = costumUserBuilder2.build()

  private val autoUserBuilder1:UserAutoBuilder= UserAutoBuilder() withPolicy(userIDPolicy) withPolicy(passwordPolicy)
  private val autoUser1:User = autoUserBuilder1.build()

  private val autoUserBuilder2:UserAutoBuilder= UserAutoBuilder()

  private var userSequence:Seq[User]= autoUserBuilder2.build(min)
  private var usernameSequence:Seq[String] = for(e<-userSequence) yield e.username
  private var passwordSequence:Seq[String]  = for(e<-userSequence) yield e.password

  s"A user created with name '${name}' and password '{$password}'" should {
    "have name" in{
      costumUser1.get.username should be (name)
    }
    "and its password should be" in {
      costumUser1.get.password should be (password)
    }
  }

  s"If a user created with a set of credential policies then user credentials" should  {

    "be complaint with the policy'${userIDPolicy}'" in{
      assert(StringPolicyChecker(userIDPolicy) check (costumUser1.get.username))
    }

    "and meet the policy'${passwordPolicy}' requirements" in{
      assert(StringPolicyChecker(passwordPolicy) check (costumUser1.get.password))
    }
  }

  s"If a user chose a credential not complaint with the given policy" should {
    "not be able to create a user" in{
      costumUser2 should be (None)
    }
  }

  s"A user auto-generated given some users' credentials " should {
    "have a name complaint with the '${userIDPolicy.getClass}'" in{
      assert(StringPolicyChecker(userIDPolicy) check (autoUser1.username))
    }
    "and a password complaint with the '${passwordPolicy.getClass}'" in {
      assert(StringPolicyChecker(passwordPolicy) check (autoUser1.password))
    }
  }

  s"User" should{
    "should be able to create a given number of users" in{
      assert(userSequence.length == min)
    }

    "which should have different username" in{
      assert(Util.countDuplicates(usernameSequence) == 0)
    }

    "and different password" in{
      assert(Util.countDuplicates(passwordSequence) == 0)
    }
  }
}
