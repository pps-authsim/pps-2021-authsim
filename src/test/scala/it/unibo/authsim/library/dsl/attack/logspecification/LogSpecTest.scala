package it.unibo.authsim.library.dsl.attack.logspecification

import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.dsl.attack.logspecification.*
import it.unibo.authsim.library.dsl.Logger

class LogSpecTest extends AnyWordSpec {
  val myLogger: Logger = Logger
  val is = afterWord("is")

  "A LogCategory" when {
    "directed to a Logger" should {
      "be implicitly converted into a LogSpec" in {
        assert((LogCategory.ALL to myLogger).isInstanceOf[LogSpec])
      }
    }
    "listed with and" can {
      "be concatenated" in {
        assert((LogCategory.TIME and LogCategory.SUCCESS to myLogger).isInstanceOf[LogSpec])
      }
    }
    "createb by a list of categories" should {
      "contain the selected categories" in {
        val categorySet = (LogCategory.ALL to myLogger).getCategories()
        assert(categorySet.size == 1 && categorySet(LogCategory.ALL))
      }
      "not contain repeated values" in {
        val categorySet = (LogCategory.TIME and LogCategory.SUCCESS and LogCategory.TIME to myLogger).getCategories()
        assert(categorySet.size == 2)
      }
    }
  }
}
