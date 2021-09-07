package it.unibo.authsim.library.dsl
import org.scalatest.wordspec.AnyWordSpec

class ProxyTest extends AnyWordSpec {
  def to = afterWord("to")
  def are = afterWord("are")
  "A user" when {
    " cretated must contain some information" which are {
      val salt = SaltInformation(Option.empty, Option.empty, Option.empty)
      val user = UserInformation("Alexandra", "Pippa", salt, Map.empty)

      "that should be equals" in {
        assert(user.username == "Alexandra")
        assert(user.password == "Pippa")
        assert(user.saltInformation == salt)
        assert(user.additionalInformation == Map.empty)
      }
    }
  }
  "however user could also be created whithout null values" when{
    val salt = SaltInformation(Option(3), Option("Policy1"), Option("value1"))
    val user = UserInformation("Lorenzo", "Cody", salt, Map("foo"-> "foo"))

    "that should be equals" in {
      assert(user.username == "Lorenzo")
      assert(user.password == "Cody")
      assert(user.saltInformation == salt)
      assert(user.additionalInformation == Map("foo"-> "foo"))
    }
  }

}
