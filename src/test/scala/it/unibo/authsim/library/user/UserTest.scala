package it.unibo.authsim.library.user

import it.unibo.authsim.library.user.{SaltInformation, UserInformation}
import org.scalatest.BeforeAndAfter
import org.scalatest.wordspec.AnyWordSpec

class UserTest extends AnyWordSpec{
  private def to = afterWord("to")
  private def are = afterWord("are")
  private def so = afterWord("so")
  private val user = UserInformation("Alexandra", "Pippa", SaltInformation())

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
      "one could choose to not salt user's password, in this case the salt information should take the default values" in {
        assert(user.saltInformation == SaltInformation())
      }
      "the same would happen id users's additional information could not be provided" in{
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
