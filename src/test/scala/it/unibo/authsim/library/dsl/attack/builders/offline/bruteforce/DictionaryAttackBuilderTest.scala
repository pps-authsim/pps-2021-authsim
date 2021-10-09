package it.unibo.authsim.library.dsl.attack.builders.offline.bruteforce

import it.unibo.authsim.library.dsl.alphabet.Dictionary
import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.UserProvider
import it.unibo.authsim.library.user.model.UserInformation
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.Duration

class DictionaryAttackBuilderTest extends AnyWordSpec:
  private class TestStatisticsConsumer extends StatisticsConsumer:
    private var statistics: Statistics = Statistics.zero

    def getStatistics: Statistics = this.statistics

    override def consume(consumable: Statistics): Unit =
      this.statistics = consumable
      println("Attempts: " + consumable.attempts)
      println("Elapsed time: " + consumable.elapsedTime.toMillis + " ms")
      println("Breached credentials: " + consumable.successfulBreaches.map(u => u.username + " - " + u.password).reduceOption((u1, u2) => u1 + "\n" + u2).getOrElse("No credentials breached"))

    val myProxy = new UserProvider {
      override def userInformations(): List[UserInformation] = List(UserInformation("mario", HashFunction.MD5().hash("hunter2"), Some(HashFunction.MD5())))

    }

    private val myLogger = new TestStatisticsConsumer()

    private val myDictionary = Dictionary(Set("password", "1", "2", "3", "4", "hunter", "user", "admin"))
    val myDictionaryAttackBuilder: DictionaryAttackBuilder = new DictionaryAttackBuilder()

    "The DictionaryAttackBuilder" must {
      myDictionaryAttackBuilder withDictionary myDictionary against myProxy
      "declare a target" in {
        assert(myDictionaryAttackBuilder.getTarget() != null)
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
        (new DictionaryAttackBuilder() withDictionary myDictionary maximumCombinedWords 2 against myProxy jobs 4 logTo myLogger).executeNow()
        assert(!myLogger.getStatistics.successfulBreaches.isEmpty)
      }
    }
