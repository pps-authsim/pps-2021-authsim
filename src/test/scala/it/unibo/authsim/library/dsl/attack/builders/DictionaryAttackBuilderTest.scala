package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.{HashFunction, Proxy, StatisticsConsumer}
import it.unibo.authsim.library.user.{SaltInformation, UserInformation}
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.Duration

class DictionaryAttackBuilderTest extends AnyWordSpec {
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
    override def getUserInformations(): List[UserInformation] = List(UserInformation("mario", HashFunction.MD5().hash("hunter2"), SaltInformation(Option.empty, Option.empty, Option.empty), Map.empty))
  }

  private val myLogger = new TestStatisticsConsumer()

  private val myDictionary = List("password", "1", "2", "3", "4", "hunter", "user", "admin")
  val myDictionaryAttackBuilder: DictionaryAttackBuilder = new DictionaryAttackBuilder()

  "The DictionaryAttackBuilder" must {
    myDictionaryAttackBuilder withDictionary myDictionary against myProxy hashingWith HashFunction.MD5()
    "declare a target" in {
      assert(myDictionaryAttackBuilder.getTarget() != null)
    }
    "select a hash function" in {
      assert(myDictionaryAttackBuilder.getHashFunction() != null)
    }
    "declare a dictionary" in {
      assert(myDictionaryAttackBuilder.getDictionary != null)
    }
  }

  it can {
    myDictionaryAttackBuilder against myProxy
    "specify where to log" in {
      myDictionaryAttackBuilder logTo myLogger
      assert(!myDictionaryAttackBuilder.getStatisticsConsumer().isEmpty)
    }
    "specify a timeout time" in {
      myDictionaryAttackBuilder timeout Duration.apply(2, scala.concurrent.duration.HOURS)
      assert(!myDictionaryAttackBuilder.getTimeout().isEmpty)
    }
    "specify the number of times words can be combined" in {
      val maximumCombinations = 2
      myDictionaryAttackBuilder maximumCombinedWords maximumCombinations
      assert(myDictionaryAttackBuilder.getMaximumCombinationLength == maximumCombinations)
    }
  }

  "A dictionary attack" should {
    "crack a simple password" in {
      (new DictionaryAttackBuilder() withDictionary myDictionary maximumCombinedWords 2 against myProxy hashingWith HashFunction.MD5() jobs 4 logTo myLogger).executeNow()
      assert(!myLogger.getStatistics.successfulBreaches.isEmpty)
    }
  }
}
