package it.unibo.authsim.library.attack.builders.offline

import it.unibo.authsim.library.UserProvider
import it.unibo.authsim.library.attack.builders.{Attack, AttackBuilder}
import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction

/**
 * Builder for offline attacks. It allows to configure a UserProvider and the number of concurrent workers.
 * The user provider is mandatory.
 */
trait OfflineAttackBuilder extends AttackBuilder:
  private var target: UserProvider = null
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