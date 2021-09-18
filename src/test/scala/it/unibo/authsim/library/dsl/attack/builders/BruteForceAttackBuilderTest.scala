package it.unibo.authsim.library.dsl.attack.builders

import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.{HashFunction, Proxy, StatisticsConsumer}
import it.unibo.authsim.library.user.{SaltInformation, UserInformation}

import scala.concurrent.duration.Duration

class BruteForceAttackBuilderTest extends AnyWordSpec {
  private class TestStatisticsConsumer extends StatisticsConsumer {
    private var statistics: Statistics = Statistics.zero

    def getStatistics: Statistics = this.statistics

    override def consume(consumable: Statistics): Unit = {
      this.statistics = consumable
      println("Attempts: " + consumable.attempts)
      println("Elapsed time: " + consumable.elapsedTime.toMillis + " ms")
      println("Breached credentials: " + consumable.successfulBreaches.map(u => u.username + " - " + u.password).reduceOption((u1, u2) => u1 + "\n" + u2).getOrElse("No credentials breached"))
    }
  }

  val myProxy = new Proxy {
    override def getUserInformations(): List[UserInformation] = List(UserInformation("mario", HashFunction.MD5().hash("abc"), SaltInformation(Option.empty, Option.empty, Option.empty), Map.empty))
  }
  private val myLogger = new TestStatisticsConsumer()
  private val myAlphabet = ConcurrentStringCombinator.lowercaseLetters
  private val maximumPasswordLength = 4
  val myBruteForceBuilder = new BruteForceAttackBuilder()

  "The BruteForceAttackBuilder" must {
    myBruteForceBuilder against myProxy hashingWith HashFunction.MD5() usingAlphabet myAlphabet
    "declare a target" in {
      assert(myBruteForceBuilder.getTarget() != null)
    }
    "select a hash function" in {
      assert(myBruteForceBuilder.getHashFunction() != null)
    }
    "declare an alphabet to use" in {
      assert(myBruteForceBuilder.getAlphabet() != null)
    }
  }

  it can {
    myBruteForceBuilder against myProxy
    "specify where to log" in {
      myBruteForceBuilder logTo myLogger
      assert(!myBruteForceBuilder.getStatisticsConsumer().isEmpty)
    }
    "specify a timeout time" in {
      myBruteForceBuilder timeout Duration.apply(2, scala.concurrent.duration.HOURS)
      assert(!myBruteForceBuilder.getTimeout().isEmpty)
    }
    "specify the maximum length of the password" in {
      myBruteForceBuilder maximumLength 2
      assert(myBruteForceBuilder.getMaximumLength == 2)
    }
  }

  "A bruteforce attack" should {
    "crack a simple password" in {
      (new BruteForceAttackBuilder() against myProxy usingAlphabet myAlphabet maximumLength maximumPasswordLength hashingWith HashFunction.MD5() jobs 4 logTo myLogger).executeNow()
      assert(!myLogger.getStatistics.successfulBreaches.isEmpty)
    }
  }
}
