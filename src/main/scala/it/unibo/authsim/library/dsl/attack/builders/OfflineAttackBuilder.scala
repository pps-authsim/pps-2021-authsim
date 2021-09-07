package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.stub.Proxy

trait OfflineAttackBuilder extends AttackBuilder {
  private var target: Proxy = null
  private var numberOfWorkers: Int = 1

  final def getTarget() = this.target
  final def getNumberOfWorkers = this.numberOfWorkers

  def against(target: Proxy): OfflineAttackBuilder = {
    this.target = target
    this
  }

  def jobs(numberOfWorkers: Int): OfflineAttackBuilder = {
    this.numberOfWorkers = numberOfWorkers
    this
  }

}

trait OfflineAttack extends Attack {

}