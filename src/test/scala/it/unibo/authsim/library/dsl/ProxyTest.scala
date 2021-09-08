package it.unibo.authsim.library.dsl
import it.unibo.authsim.library.user.UserInformation
import org.scalatest.wordspec.AnyWordSpec

class ProxyTest extends AnyWordSpec {
  def to = afterWord("to")
  def are = afterWord("are")
  "A user" when {
    " cretated must contain some information" which are {
      val salt = SaltInformation(Option.empty, Option.empty, Option.empty)
      val user = UserInformation("Alexandra", "Pippa", salt, Map.empty)
      "therefore user's name should be" in {
        assert(user.username == "Alexandra")
      }
      "user's password should be" in {
        assert(user.password == "Pippa")
      }
      "the salt value associated should be" in {
        assert(user.saltInformation == salt)
      }
      "finally users's additional information should be" in{
        assert(user.additionalInformation == Map.empty)
      }
    }
  }
  "however one could also create a user whithout null values" when{
    val salt2 = SaltInformation(Option(3), Option("Policy1"), Option("value1"))
    val user2 = UserInformation("Lorenzo", "Cody", salt2, Map.empty)
    "in this case user's name should be" in {
      assert(user2.username == "Lorenzo")
    }
    "user's password should be" in {
      assert(user2.password == "Cody")
    }
    "the salt value associated should be" in {
      assert(user2.saltInformation == salt2)
    }
    "finally users's additional information should be" in{
      assert(user2.additionalInformation == Map.empty)
    }
  }

}
