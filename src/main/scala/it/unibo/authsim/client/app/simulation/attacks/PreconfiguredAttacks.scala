package it.unibo.authsim.client.app.simulation.attacks

object PreconfiguredAttacks extends Enumeration {

  type AttackConfiguration = Value

  val BruteForceLowers, BruteForceLetters, BruteForceAll, DictionaryMostCommonPasswords = Value

}
