package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.{HashFunction, UserProvider}

trait OfflineAttackBuilder extends AttackBuilder {
  private var target: UserProvider = null
  private var hashFunction: HashFunction = null
  private var numberOfWorkers: Int = 1

  final def getTarget() = this.target
  final def getHashFunction() = this.hashFunction
  final def getNumberOfWorkers = this.numberOfWorkers

  def against(target: UserProvider): OfflineAttackBuilder = {
    this.target = target
    this
  }

  def jobs(numberOfWorkers: Int): OfflineAttackBuilder = {
    this.numberOfWorkers = numberOfWorkers
    this
  }

  def hashingWith(hashFunction: HashFunction): OfflineAttackBuilder = {
    this.hashFunction = hashFunction
    this
  }
}

trait OfflineAttack extends Attack {

}