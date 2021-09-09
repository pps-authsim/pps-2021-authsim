package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.attack.logspecification.LogSpec
import it.unibo.authsim.library.dsl.{HashFunction, Proxy}
import it.unibo.authsim.library.dsl.attack.statistics.Statistics

import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

class BruteForceAttackBuilder extends OfflineAttackBuilder {

  def save(): BruteForceAttack = new BruteForceAttack(this.getTarget(), this.getHashFunction(), this.getLogSpecification(), this.getTimeout(), this.getNumberOfWorkers)

  def executeNow(): Unit = this.save().start()
}

class BruteForceAttack(private val target: Proxy, private val hashFunction: HashFunction, private val logTo: Option[LogSpec], private val timeout: Option[Duration], private val jobs: Int) extends OfflineAttack {

  override def start(): Unit = {
    val jobResults: List[Future[Statistics]] = List.empty
    var totalResults: Statistics = new Statistics(Set(), 0, Duration.Zero)
    val startTime = System.nanoTime()
    // TODO: create futures
    /* TODO: each future must get a string,
        apply the hash function and compare the result with the correct password,
        until there are not more strings
    */
    jobResults.foreach(future => future.foreach(stats => totalResults = totalResults + stats))
    val endTime = System.nanoTime()
    val elapsedTime = Duration(endTime - startTime, MILLISECONDS)
    // TODO: logTo
  }
}
