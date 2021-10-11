package it.unibo.authsim.client.app.simulation.attacks

enum AttackConfiguration:

  case BruteForceLowers extends AttackConfiguration
  case BruteForceLetters extends AttackConfiguration
  case BruteForceAll extends AttackConfiguration
  case DictionaryMostCommonPasswords extends AttackConfiguration

