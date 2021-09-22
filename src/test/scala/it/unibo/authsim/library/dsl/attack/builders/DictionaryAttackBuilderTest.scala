package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.attack.logspecification.{LogCategory, LogSpec}
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.{HashFunction, Logger, UserProvider}
import it.unibo.authsim.library.user.model.{CryptoInformation, UserInformation}
import org.mockito.Mock
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar.mock

import scala.concurrent.duration.Duration

class DictionaryAttackBuilderTest extends AnyWordSpec {
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
    override def userInformations(): List[UserInformation] = List(UserInformation("mario", HashFunction.MD5().hash("hunter2"), CryptoInformation(algorithmPolicy), Map.empty))
  }

  private val myLogger = new TestLogger()

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
      myDictionaryAttackBuilder log (LogCategory.ALL to myLogger)
      assert(!myDictionaryAttackBuilder.getLogSpecification().isEmpty)
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
      (new DictionaryAttackBuilder() withDictionary myDictionary maximumCombinedWords 2 against myProxy hashingWith HashFunction.MD5() jobs 4 log (LogCategory.ALL to myLogger)).executeNow()
      assert(myLogger.getCrackStatus)
    }
  }
}
