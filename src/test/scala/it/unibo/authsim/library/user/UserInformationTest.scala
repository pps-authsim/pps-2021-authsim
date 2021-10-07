package it.unibo.authsim.library.user

import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.DES
import it.unibo.authsim.library.user.builder.UserInformationBuilder
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{OTPPolicyBuilder, PasswordPolicyBuilder, SaltPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, OTPPolicy, PasswordPolicy, SaltPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.util.Util
import it.unibo.authsim.library.user.model.UserInformation
import org.scalatest.BeforeAndAfter
import org.scalatest.wordspec.AnyWordSpec

class UserInformationTest extends AnyWordSpec{
  private def to = afterWord("to")
  private def are = afterWord("are")
  private def so = afterWord("so")

  private val min= 5
  private val name= Util.generateRandomString(min)
  private val encryptedPassword= Util.generateRandomString(min)
  private val algorithm = DES()

  private var userBuilder1 : UserInformationBuilder = UserInformationBuilder() withUserName(name) withPassword(encryptedPassword)
  private var userInformation1 : Option[UserInformation] = userBuilder1.build
  private var userBuilder2 : UserInformationBuilder = UserInformationBuilder() withUserName(name) withPassword(encryptedPassword) withAlgorithm(algorithm)
  private var userInformation2 : Option[UserInformation] = userBuilder2.build

  private var userBuilder3 = UserInformationBuilder() withUserName(name)
  private var userInformation3 : Option[UserInformation] = userBuilder3.build

  "A userInformation" when {
    "created" should {
      "have a name" in{
        assert(userInformation1.get.username == name)
      }
      "and a password" in {
        assert(userInformation1.get.password == encryptedPassword)
      }
      "that could not be encrypted" in {
        assert(userInformation1.get.algorithm == (None))
      }
    }
  }
  "however one could also decide to" when{
    "but user's name must always be provided" in {
      assert(userInformation2.get.username == name)
    }
    "as well as user's password should be" in {
      assert(userInformation2.get.password == encryptedPassword)
    }
    "the salt value if provided should be" in {
      assert(userInformation2.get.algorithm != None)
    }
  }

  "Finally" when{
    "one try to be a user information without providing the credentials values should get no UserInformation" in {
      assert(userInformation3 == None)
    }
  }
}
