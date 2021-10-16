package it.unibo.authsim.client.app.simulation.attacks

/**
 * An enum representing a preconfigured attack
 */
enum AttackConfiguration:

  case BruteForceLowers extends AttackConfiguration
  case BruteForceLetters extends AttackConfiguration
  case BruteForceAll extends AttackConfiguration
  case DictionaryMostCommonPasswords extends AttackConfiguration
  
  case GuessDefaultPassword extends AttackConfiguration

