package it.unibo.authsim.library.dsl.attack.builders

import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.dsl.stub._
import it.unibo.authsim.library.dsl.attack.logspecification._
import com.github.nscala_time.time.Imports._

class BruteForceAttackBuilderTest extends AnyWordSpec {
  val myProxy = new Proxy() { }
  val myLogger = new Logger() { }
  val myBruteForceBuilder = new BruteForceAttackBuilder()

  "The BruteForceAttackBuilder" must {
    myBruteForceBuilder against myProxy
    "declare a target" in {
      assert(myBruteForceBuilder.getTarget() != null)
    }
  }

  it can {
    myBruteForceBuilder against myProxy
    "specify where to log" in {
      myBruteForceBuilder log (LogCategory.ALL to myLogger)
      assert(!myBruteForceBuilder.getLogSpecification().isEmpty)
    }
    "specify a timeout time" in {
      myBruteForceBuilder timeout 2.hours.toDuration
      assert(!myBruteForceBuilder.getTimeout().isEmpty)
    }
  }
}
