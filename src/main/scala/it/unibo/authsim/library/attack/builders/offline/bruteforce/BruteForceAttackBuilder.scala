package it.unibo.authsim.library.attack.builders.offline.bruteforce

import it.unibo.authsim.library.attack.builders.offline.bruteforce.AbstractBruteForceAttackBuilder
import it.unibo.authsim.library.alphabet.{Alphabet, SymbolicAlphabet}

/**
 * A builder of bruteforce attacks.
 */
class BruteForceAttackBuilder extends AbstractBruteForceAttackBuilder[SymbolicAlphabet]:
  def usingAlphabet(alphabet: Alphabet[SymbolicAlphabet]) = super.protectedUsingAlphabet(alphabet)

  def getAlphabet = super.protectedGetAlphabet

  def maximumLength(maximumLength: Int) = super.protectedMaximumLength(maximumLength)

  def getMaximumLength = super.protectedGetMaximumLength
