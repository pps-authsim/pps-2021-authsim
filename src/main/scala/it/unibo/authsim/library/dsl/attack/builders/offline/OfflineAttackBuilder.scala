package it.unibo.authsim.library.dsl.attack.builders.offline

import it.unibo.authsim.library.dsl.attack.builders.{Attack, AttackBuilder}
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.UserProvider

/**
 * Builder for offline attacks. It allows to configure a UserProvider, the used hash function and the number of concurrent workers.
 */
trait OfflineAttackBuilder extends AttackBuilder:
  private var target: UserProvider = null
  private var hashFunction: HashFunction = null
  private var numberOfWorkers: Int = 1

  /**
   * @return The UserProvider
   */
  final def getTarget() = this.target

  /**
   * @return The number of concurrent workers used by the attack.
   */
  final def getNumberOfWorkers = this.numberOfWorkers

  /**
   * Sets the UserProvider to retrieve the users from.
   * @param target The UserProvider
   * @return The builder
   */
  def against(target: UserProvider): this.type = this.builderMethod[UserProvider](provider => this.target = provider)(target)

  /**
   * Sets the number of maximum concurrent workers.
   * @param numberOfWorkers Maximum number of workers to set.
   * @return The builder
   */
  def jobs(numberOfWorkers: Int): this.type = this.builderMethod[Int](n => this.numberOfWorkers = n)(numberOfWorkers)

/**
 * An offline attack.
 */
trait OfflineAttack extends Attack