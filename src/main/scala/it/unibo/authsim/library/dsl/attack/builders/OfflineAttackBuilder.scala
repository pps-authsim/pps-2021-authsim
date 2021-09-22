package it.unibo.authsim.library.dsl.attack.builders

import it.unibo.authsim.library.dsl.{HashFunction, UserProvider}

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
   * @return The hash function to use to hash passwords.
   */
  final def getHashFunction() = this.hashFunction

  /**
   * @return The number of concurrent workers used by the attack.
   */
  final def getNumberOfWorkers = this.numberOfWorkers

  /**
   * Sets the UserProvider to retrieve the users from.
   * @param target The UserProvider
   * @return The builder
   */
  def against(target: UserProvider): this.type =
    this.target = target
    this

  /**
   * Sets the number of maximum concurrent workers.
   * @param numberOfWorkers Maximum number of workers to set.
   * @return The builder
   */
  def jobs(numberOfWorkers: Int): this.type =
    this.numberOfWorkers = numberOfWorkers
    this

  /**
   * Sets the hash function to use in the attack.
   * @param hashFunction The hash function.
   * @return The builder.
   */
  def hashingWith(hashFunction: HashFunction): this.type =
    this.hashFunction = hashFunction
    this

/**
 * An offline attack.
 */
trait OfflineAttack extends Attack