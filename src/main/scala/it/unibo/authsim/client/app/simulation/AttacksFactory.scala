package it.unibo.authsim.client.app.simulation

import it.unibo.authsim.library.dsl.UserProvider
import it.unibo.authsim.library.dsl.alphabet.SymbolicAlphabet
import it.unibo.authsim.library.dsl.attack.builders.ConcurrentStringCombinator
import it.unibo.authsim.library.dsl.attack.builders.offline.bruteforce.BruteForceAttackBuilder
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.attack.builders.AttackBuilder
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration

class AttacksFactory(private val userProvider: UserProvider, private val logger: StatisticsConsumer):

  private val defaultAlphabets = new PolicyDefaultAlphabet()
  /**
   * @return an AttackBuilder configured to attack a userProvider, using lowercase characters to construct the password string
   *         with a maximum length of 6 and logging to the given logger, without timeout.
   */
  def bruteForceLowers(): AttackBuilder = new BruteForceAttackBuilder() against userProvider usingAlphabet defaultAlphabets.lowers logTo logger maximumWordLength 6

  /**
   * @return an AttackBuilder configured to attack a userProvider, using both lower and upper case characters to construct the password string
   *         with a maximum length of 10 and logging to the given logger, with a timeout of 120 seconds (2 minutes).
   */
  def bruteForceLetters(): AttackBuilder = new BruteForceAttackBuilder() against userProvider usingAlphabet (defaultAlphabets.lowers and defaultAlphabets.uppers) maximumWordLength 10 logTo logger timeout Duration(120, TimeUnit.SECONDS)

  /**
   * @return an AttackBuilder configured to attack a userProvider, using all alphanumeric and symbols characters to construct the password string
   *         with a maximum length of 16 and logging to the given logger, with a timeout of 600 seconds (10 minutes).
   */
  def bruteForceAll(): AttackBuilder = new BruteForceAttackBuilder() against userProvider usingAlphabet defaultAlphabets.alphanumericsymbols maximumWordLength 16 logTo logger timeout Duration(600, TimeUnit.SECONDS)
