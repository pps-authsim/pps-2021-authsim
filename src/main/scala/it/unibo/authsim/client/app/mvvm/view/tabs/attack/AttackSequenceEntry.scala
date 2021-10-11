package it.unibo.authsim.client.app.mvvm.view.tabs.attack

import it.unibo.authsim.client.app.simulation.attacks.PreconfiguredAttacks.AttackConfiguration

case class AttackSequenceEntry(sequence: String, description: String, attack:AttackConfiguration):
  override def toString: String = sequence
