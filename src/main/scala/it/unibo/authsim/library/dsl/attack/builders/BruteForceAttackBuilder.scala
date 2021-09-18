package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.{HashFunction, Proxy}
import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer
import it.unibo.authsim.library.user.User

import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, Future}
import scala.concurrent.TimeoutException
import concurrent.ExecutionContext.Implicits.global

class BruteForceAttackBuilder extends OfflineAttackBuilder:
  private var alphabet: List[String] = null
  private var maximumLength = 1

  def usingAlphabet(alphabet: List[String]): this.type =
    this.alphabet = alphabet
    this

  def getAlphabet(): List[String] = this.alphabet

  def maximumLength(maximumLength: Int): this.type =
    this.maximumLength = maximumLength
    this

  def getMaximumLength: Int = this.maximumLength

  def save(): BruteForceAttack = new BruteForceAttack(this.getTarget(), this.getHashFunction(), this.getAlphabet(), this.getMaximumLength, this.getStatisticsConsumer(), this.getTimeout(), this.getNumberOfWorkers)

  def executeNow(): Unit = this.save().start()

class BruteForceAttack(private val target: Proxy, private val hashFunction: HashFunction, private val alphabet: List[String], private val maximumLength: Int, private val logTo: Option[StatisticsConsumer], private val timeout: Option[Duration], private val jobs: Int) extends OfflineAttack:

  override def start(): Unit =
    var jobResults: List[Future[Statistics]] = List.empty
    var totalResults = Statistics.zero
    val monitor = new ConcurrentStringCombinator(alphabet, maximumLength)
    val startTime = System.nanoTime()
    (1 to jobs).foreach(_ => jobResults = Future(futureJob(target.getUserInformations().head, monitor)) :: jobResults)
    // TODO: refine timeout
    try {
      jobResults.foreach(future => totalResults = totalResults + Await.result(future, timeout.getOrElse(Duration.Inf)))
    } catch {
      case e: TimeoutException => println("Timeout")
    }
    val endTime = System.nanoTime()
    val elapsedTime = Duration(endTime - startTime, MILLISECONDS)
    totalResults = totalResults + new Statistics(Set(), attempts = 0, elapsedTime / jobs)
    logTo.foreach(logSpec => logSpec.consume(totalResults))

  private def futureJob(targetUser: User, stringProvider: ConcurrentStringCombinator): Statistics =
    var localStatistics = Statistics.zero
    var nextPassword = stringProvider.getNextString()
    while !nextPassword.isEmpty do
      val nextPasswordString = nextPassword.get
      val hashedPassword = hashFunction.hash(nextPasswordString)
      localStatistics = localStatistics + new Statistics(hashedPassword == targetUser.password match {
        case true => Set(new User {
          override def username: String = targetUser.username
          override def password: String = nextPasswordString
        })
        case false => Set()
      }, attempts = 1, Duration.Zero)
      nextPassword = stringProvider.getNextString()
    end while
    localStatistics
