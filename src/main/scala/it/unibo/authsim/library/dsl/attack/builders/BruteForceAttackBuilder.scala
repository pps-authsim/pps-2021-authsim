package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.attack.logspecification.LogSpec
import it.unibo.authsim.library.dsl.stub._
import org.joda.time.Duration

class BruteForceAttackBuilder extends OfflineAttackBuilder {

  def save(): BruteForceAttack = new BruteForceAttack(this.getTarget(), this.getLogSpecification(), this.getTimeout(), this.getNumberOfWorkers)

  def executeNow(): Unit = this.save().start()
}

class BruteForceAttack(private val target: Proxy, private val logTo: Option[LogSpec], private val timeout: Option[Duration], private val jobs: Int) extends OfflineAttack {

  override def start(): Unit = ???
}
