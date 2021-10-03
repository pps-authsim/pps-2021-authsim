package it.unibo.authsim.library.user

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{OTPPolicyBuilder, PasswordPolicyBuilder, SaltPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, OTPPolicy, PasswordPolicy, SaltPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.model.{CryptoInformation, UserInformation}
import org.scalatest.BeforeAndAfter
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar.mock

class UserTest extends AnyWordSpec{
  private def to = afterWord("to")
  private def are = afterWord("are")
  private def so = afterWord("so")


  //TODO: qui ci dovrò mettere le algorithm policy non il mock
  import org.mockito.Mock
  @Mock abstract class AlgorithmPolicy extends Policy
  @Mock val algorithmPolicy = mock[AlgorithmPolicy]
  private val cryptoInfo = CryptoInformation(algorithmPolicy)
  private val user = UserInformation("Alexandra", "Pippa", CryptoInformation(algorithmPolicy))
  private val user2 = UserInformation("Lorenzo", "Cody", cryptoInfo)

  "A user" when {
    "created" should {
      "have a name" in{
        assert(user.username == "Alexandra")
      }
      "and a password" in {
        assert(user.password == "Pippa")
      }
      "one could choose to not salt user's password, in this case the salt information should take the default values" in {
        assert(user.cryptoInformation == CryptoInformation(algorithmPolicy))
      }
    }
  }
  "however one could also create a user whithout null values" when{
    "but user's name must always be provided" in {
      assert(user2.username == "Lorenzo")
    }
    "as well as user's password should be" in {
      assert(user2.password == "Cody")
    }
    "the salt value if provided should be" in {
      assert(user2.cryptoInformation == cryptoInfo)
    }
  }

}
