package it.unibo.authsim.library.dsl.attack.builders

import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.library.dsl.attack.logspecification.*
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.{HashFunction, Logger, UserProvider}
import it.unibo.authsim.library.user.model.{CryptoInformation, UserInformation}
import org.mockito.Mock
import org.scalatestplus.mockito.MockitoSugar.mock

import scala.concurrent.duration.Duration

class BruteForceAttackBuilderTest extends AnyWordSpec {
  private class TestLogger extends Logger {
    private var execTime = 0L
    private var statistics: Map[String, String] = Map.empty
    private var isCracked: Boolean = false

    override def receiveExecutionTime(executionTime: Long): Unit = this.execTime = executionTime

    override def receiveStatistics(map: Map[String, String]): Unit = this.statistics = map

    override def receiveCracked(flag: Boolean): Unit = this.isCracked = flag

    def getCrackStatus: Boolean = this.isCracked
  }
  //TODO Alex appena abbiamo le policy degli algoritmi togli sta roba, te l'ho messa se no non compilava più nulla
  @Mock abstract class AlgorithmPolicy extends Policy
  @Mock val algorithmPolicy = mock[AlgorithmPolicy]

  val myProxy = new UserProvider {
    override def userInformations(): List[UserInformation] = List(UserInformation("mario", HashFunction.MD5().hash("abc"), CryptoInformation(algorithmPolicy), Map.empty))
  }
  private val myLogger = new TestLogger()
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
      myBruteForceBuilder log (LogCategory.ALL to myLogger)
      assert(!myBruteForceBuilder.getLogSpecification().isEmpty)
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
      (new BruteForceAttackBuilder() against myProxy usingAlphabet myAlphabet hashingWith HashFunction.MD5() jobs 4 log (LogCategory.ALL to myLogger)).executeNow()
      assert(myLogger.getCrackStatus)
    }
  }
}
