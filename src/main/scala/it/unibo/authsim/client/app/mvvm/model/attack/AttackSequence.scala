package it.unibo.authsim.client.app.mvvm.model.attack

import it.unibo.authsim.client.app.simulation.attacks.PreconfiguredAttacks.AttackConfiguration

case class AttackSequence(sequence: String, description: String, attack: AttackConfiguration)
