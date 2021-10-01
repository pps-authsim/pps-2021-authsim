package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.attack.builders.offline.bruteforce.BruteForceAttackBuilder
import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.{HashFunction, UserProvider}
import it.unibo.authsim.library.user.model.{CryptoInformation, UserInformation}
import org.mockito.Mock
import org.scalatestplus.mockito.MockitoSugar.mock

import scala.concurrent.duration.Duration

class BruteForceAttackBuilderTest extends AnyWordSpec:

  private class TestStatisticsConsumer extends StatisticsConsumer:
    private var statistics: Statistics = Statistics.zero

    def getStatistics: Statistics = this.statistics

    override def consume(consumable: Statistics): Unit =
      this.statistics = consumable
      println("Attempts: " + consumable.attempts)
      println("Elapsed time: " + consumable.elapsedTime.toMillis + " ms")
      println("Breached credentials: " + consumable.successfulBreaches.map(u => u.username + " - " + u.password).reduceOption((u1, u2) => u1 + "\n" + u2).getOrElse("No credentials breached"))

  //TODO Alex appena abbiamo le policy degli algoritmi togli sta roba, te l'ho messa se no non compilava più nulla
  @Mock
  abstract class AlgorithmPolicy extends Policy
  @Mock
  val algorithmPolicy = mock[AlgorithmPolicy]

    val myProxy = new UserProvider {
      override def userInformations(): List[UserInformation] = List(UserInformation("mario", HashFunction.MD5().hash("abc"), CryptoInformation(algorithmPolicy)))
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
      "timeout if out of time" in {
        val consumer = new TestStatisticsConsumer()
        (new BruteForceAttackBuilder() against myProxy usingAlphabet myAlphabet maximumLength maximumPasswordLength hashingWith HashFunction.MD5() jobs 4 logTo consumer timeout Duration.Zero).executeNow()
        assert(consumer.getStatistics.timedOut)
      }
    }

