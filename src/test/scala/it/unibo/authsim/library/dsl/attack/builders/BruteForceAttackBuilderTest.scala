package it.unibo.authsim.library.dsl.attack.builders

import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.dsl.attack.logspecification.*
import it.unibo.authsim.library.dsl.{HashFunction, Logger, Proxy}

import scala.concurrent.duration.Duration

class BruteForceAttackBuilderTest extends AnyWordSpec {
  val myProxy = Proxy()
  val myLogger = Logger()
  val myBruteForceBuilder = new BruteForceAttackBuilder()

  "The BruteForceAttackBuilder" must {
    myBruteForceBuilder against myProxy hashingWith HashFunction.MD5()
    "declare a target" in {
      assert(myBruteForceBuilder.getTarget() != null)
    }
    "select a hash function" in {
      assert(myBruteForceBuilder.getHashFunction() != null)
    }
  }

  it can {
    myBruteForceBuilder against myProxy
    "specify where to log" in {
      myBruteForceBuilder log (LogCategory.ALL to myLogger)
      assert(!myBruteForceBuilder.getLogSpecification().isEmpty)
    }
    "specify a timeout time" in {
      myBruteForceBuilder timeout Duration.apply(2, scala.concurrent.duration.HOURS)
      assert(!myBruteForceBuilder.getTimeout().isEmpty)
    }
  }
}
