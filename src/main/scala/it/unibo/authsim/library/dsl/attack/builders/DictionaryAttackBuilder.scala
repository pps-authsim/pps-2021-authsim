package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.{HashFunction, Proxy}
import it.unibo.authsim.library.dsl.attack.logspecification.{LogCategory, LogSpec}
import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.user.User

import scala.concurrent.{Await, Future, TimeoutException}
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.ExecutionContext.Implicits.global

class DictionaryAttackBuilder extends OfflineAttackBuilder {
  private var dictionary: List[String] = null
  private var maximumCombinedWords: Int = 1

  def withDictionary(dictionary: List[String]): DictionaryAttackBuilder = {
    this.dictionary = dictionary
    this
  }

  def maximumCombinedWords(max: Int): DictionaryAttackBuilder = {
    this.maximumCombinedWords = max
    this
  }

  def getDictionary: List[String] = this.dictionary
  def getMaximumCombinationLength: Int = maximumCombinedWords

  override def save(): Attack = new DictionaryAttack(this.getTarget(), this.getHashFunction(), this.getDictionary, this.getMaximumCombinationLength, this.getLogSpecification(), this.getTimeout(), this.getNumberOfWorkers)

  override def executeNow(): Unit = this.save().start()
}

class DictionaryAttack(private val target: Proxy, private val hashFunction: HashFunction, private val dictionary: List[String], private val maximumCombinations: Int, private val logTo: Option[LogSpec], private val timeout: Option[Duration], private val jobs: Int) extends OfflineAttack {
  override def start(): Unit = {
    var jobResults: List[Future[Statistics]] = List.empty
    var totalResults = Statistics.zero
    val monitor = new ConcurrentStringCombinator(dictionary, maximumCombinations)
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
    // TODO: refine logging
    logTo.foreach(logSpec => {
      if (logSpec.getCategories()(LogCategory.SUCCESS) || logSpec.getCategories()(LogCategory.ALL)) then
        logSpec.getTargetLogger().foreach(logger => logger.receiveCracked(true))
      end if
      if (logSpec.getCategories()(LogCategory.ATTEMPTS) || logSpec.getCategories()(LogCategory.ALL)) then
        logSpec.getTargetLogger().foreach(logger => logger.receiveStatistics(Map("attempts" -> totalResults.attempts.toString, "Users" -> totalResults.successfulBreaches.map(u => u.username + " - " + u.password).toString())))
      end if
      if (logSpec.getCategories()(LogCategory.TIME) || logSpec.getCategories()(LogCategory.ALL)) then
        logSpec.getTargetLogger().foreach(logger => logger.receiveExecutionTime(elapsedTime.toMillis))
      end if
    })
  }

  private def futureJob(targetUser: User, stringProvider: ConcurrentStringCombinator): Statistics = {
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
  }
}
