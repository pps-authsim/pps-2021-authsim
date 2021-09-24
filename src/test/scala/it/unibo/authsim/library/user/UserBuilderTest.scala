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
import it.unibo.authsim.library.user.builder.util.RandomStringGenerator


class UserBuilderTest extends AnyWordSpec with should.Matchers{
  private val userIDPolicy: CredentialPolicy = UserIDPolicyBuilder() minimumLength 5 build
  private val passwordPolicy: CredentialPolicy = PasswordPolicyBuilder() minimumUpperChars 3 minimumLength 8 build
  private val name: String= RandomStringGenerator.generateRandomString(5)
  private val shortName: String= RandomStringGenerator.generateRandomString(2)
  private val password: String= RandomStringGenerator.generateRandomString(8)

  private val costumUserBuilder1:UserCostumBuilder = UserCostumBuilder() withName(name) withPassword(password)
  private val costumUser1 :Option[User]= costumUserBuilder1.build()

  private val costumUserBuilder2:UserCostumBuilder =  UserCostumBuilder() withName(name) withPassword(password) withPolicy(userIDPolicy)
  private val costumUser2:Option[User] = costumUserBuilder2.build()

  private val costumUserBuilder3:UserCostumBuilder =  UserCostumBuilder() withName(shortName) withPassword(password) withPolicy(userIDPolicy)
  private val costumUser3:Option[User] = costumUserBuilder3.build()

  private val autoUserBuilder1:UserAutoBuilder= UserAutoBuilder() withPolicy(userIDPolicy)
  private val autoUser1:User = autoUserBuilder1.build()

  private val autoUserBuilder2:UserAutoBuilder= UserAutoBuilder() withPolicy(userIDPolicy) withPolicy(passwordPolicy)
  private val autoUser2:User = autoUserBuilder2.build()

  private val seq:Seq[User]=autoUserBuilder1.asInstanceOf[UserAutoBuilder].numberOfUsers(2)
  s"A user created with name '${name}' and password '{$password}'" should {
    "have name" in{
      costumUser1.get.username should be (name)
    }
    "and its password should be" in {
      costumUser1.get.password should be (password)
    }
  }

  s"If a user created with a '${userIDPolicy.getClass}' then user credentials" should  {
    "be complaint with the policy'${userIDPolicy}'" in{
      assert(StringPolicyChecker(userIDPolicy.asInstanceOf[UserIDPolicy]) check (costumUser1.get.username))
    }
  }

  s"If a user chose a credential not complaint with the given policy" should {
    "not be able to create a user" in{
      costumUser3 should be (None)
    }
  }

  s"A user auto-generated given some users' credentials " should {
    "have a name complaint with the '${userIDPolicy.getClass}'" in{
      assert(StringPolicyChecker(userIDPolicy.asInstanceOf[UserIDPolicy]) check (autoUser1.username))
    }
    "and a password complaint with the '${passwordPolicy.getClass}'" in {
      assert(StringPolicyChecker(userIDPolicy.asInstanceOf[UserIDPolicy]) check (autoUser1.password))
    }
  }
  println("seq"+ seq)
  var listUser:List[String]= List.empty[String]
  var listPassword:List[String]= List.empty[String]
  seq.map(e=>
    listUser= e.username +:listUser
    listPassword= e.password +:listPassword
  )
  val number1=RandomStringGenerator.countDuplicates(listUser.toSeq)
  val number2=RandomStringGenerator.countDuplicates(listPassword.toSeq)
  println("List users " + (listUser, number1))
  println("List passwords " +(listPassword, number2))
  s"User" should{
    "be able to create multiple users" in{
      assert(seq.length==2)
    }
    //TODO demostrate that they are created using different credentials
  }
}