package it.unibo.authsim.library.dsl
import it.unibo.authsim.library.user.UserInformation
import it.unibo.authsim.library.user.SaltInformation
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfter}

class UserTest extends AnyWordSpec{
  private def to = afterWord("to")
  private def are = afterWord("are")

  private val salt = SaltInformation(Option.empty, Option.empty, Option.empty)
  private val user = UserInformation("Alexandra", "Pippa", salt, Map.empty)

  private val salt2 = SaltInformation(Option(3), Option("Policy1"), Option("value1"))
  private val user2 = UserInformation("Lorenzo", "Cody", salt2, Map.empty)

  "A user" when {
    "created" should {
      "have a name" in{
        assert(user.username == "Alexandra")
      }
      "and a password" in {
        assert(user.password == "Pippa")
      }
      "however one can choose to not salt user's password so saltvalues should be" in {
        assert(user.saltInformation== salt)
      }
      "also users's additional information could not be provided" in{
        assert(user.additionalInformation == Map.empty)
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
      assert(user2.saltInformation == salt2)
    }
    "finally users's additional information if set should be" in{
      assert(user2.additionalInformation == Map.empty)
    }
  }

}
