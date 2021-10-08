package it.unibo.authsim.client.app.simulation

import it.unibo.authsim.library.dsl.UserProvider
import it.unibo.authsim.library.dsl.alphabet.SymbolicAlphabet
import it.unibo.authsim.library.dsl.attack.builders.ConcurrentStringCombinator
import it.unibo.authsim.library.dsl.attack.builders.offline.bruteforce.BruteForceAttackBuilder
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.attack.builders.AttackBuilder
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet

class AttacksFactory(private val userProvider: UserProvider, private val logger: StatisticsConsumer):

  private def defaultAlphabet: SymbolicAlphabet =
    new PolicyDefaultAlphabet().alphanumericsymbols

  def bruteForceSimple(): AttackBuilder = new BruteForceAttackBuilder() against userProvider hashingWith HashFunction.MD5() usingAlphabet defaultAlphabet logTo logger

