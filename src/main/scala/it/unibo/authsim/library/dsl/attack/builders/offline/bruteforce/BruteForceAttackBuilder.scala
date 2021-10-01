package it.unibo.authsim.library.dsl.attack.builders.offline.bruteforce

import it.unibo.authsim.library.dsl.attack.builders.offline.{OfflineAttack, OfflineAttackBuilder}
import it.unibo.authsim.library.dsl.attack.builders.{Attack, BruteForceAttack, ConcurrentStringCombinator}
import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer
import it.unibo.authsim.library.dsl.{HashFunction, UserProvider}
import it.unibo.authsim.library.user.model.User

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, Future, TimeoutException}

/**
 * A builder of bruteforce attacks.
 */
// TODO: create abstract class to generalize Bruteforce and Dictionary attacks
class BruteForceAttackBuilder extends OfflineAttackBuilder:
  private var alphabet: List[String] = null
  private var maximumLength = 1

  /**
   * Sets the alphabet to use for the attack.
   *
   * A bare bruteforce attack should have an alphabet with only 1-length symbols,
   * while other attacks can use a different alphabet
   * (for example, a dictionary attack uses the dictionary itself as the alphabet).
   * @param alphabet The alphabet to use.
   * @return The builder.
   */
  // TODO: Change into a real Alphabet
  def usingAlphabet(alphabet: List[String]): this.type = this.builderMethod[List[String]](alphabet => this.alphabet = alphabet)(alphabet)

  /**
   * @return The used alphabet.
   */
  def getAlphabet(): List[String] = this.alphabet

  /**
   * Sets the maximum number of combinations of symbols from the alphabet.
   * Defaults to 1 (which means the attack only uses the provided symbols, without combining them).
   * @param maximumLength The maximum combination number.
   * @return The builder.
   */
  def maximumLength(maximumLength: Int): this.type = this.builderMethod[Int](l => this.maximumLength = l)(maximumLength)

  /**
   * @return The maximum combination number.
   */
  def getMaximumLength: Int = this.maximumLength

  override def build: Attack = new BruteForceAttack(this.getTarget(), this.getHashFunction(), this.getAlphabet(), this.getMaximumLength, this.getStatisticsConsumer(), this.getTimeout(), this.getNumberOfWorkers)

private class BruteForceAttack(private val target: UserProvider, private val hashFunction: HashFunction, private val alphabet: List[String], private val maximumLength: Int, private val logTo: Option[StatisticsConsumer], private val timeout: Option[Duration], private val jobs: Int) extends OfflineAttack:

  override def start(): Unit =
    var jobResults: List[Future[Statistics]] = List.empty
    var totalResults = Statistics.zero
    val monitor = new ConcurrentStringCombinator(alphabet, maximumLength)
    val startTime = System.nanoTime()
    (1 to jobs).foreach(_ => jobResults = Future(futureJob(target.userInformations(), monitor)) :: jobResults)
    try {
      Await.result(Future.sequence(jobResults), timeout.getOrElse(Duration.Inf)).foreach(stats => totalResults = totalResults + stats)
    } catch {
      case e: TimeoutException => totalResults = totalResults + Statistics.timedOut
    }
    val endTime = System.nanoTime()
    val elapsedTime = Duration(endTime - startTime, MILLISECONDS)
    totalResults = totalResults + Statistics.onlyElapsedTime(elapsedTime)
    logTo.foreach(logSpec => logSpec.consume(totalResults))

  private def futureJob(targetUsers: List[User], stringProvider: ConcurrentStringCombinator): Statistics =
    var localStatistics = Statistics.zero
    var nextPassword = stringProvider.getNextString()
    while !nextPassword.isEmpty do
      val nextPasswordString = nextPassword.get
      val hashedPassword = hashFunction.hash(nextPasswordString)
      targetUsers.foreach(user => {
        localStatistics = localStatistics + Statistics.onlyBreaches(hashedPassword == user.password match {
          case true => Set(User(user.username, nextPasswordString))
          case false => Set()
        })
      })
      localStatistics = localStatistics + Statistics.onlyAttempts(attempts = 1)
      nextPassword = stringProvider.getNextString()
    end while
    localStatistics
