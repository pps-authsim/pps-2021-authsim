package it.unibo.authsim.client.app.mvvm.model.attack

import it.unibo.authsim.client.app.simulation.attacks.AttackConfiguration

/**
 * Represents attack sequence and its data
 * @param sequence attack sequence display name
 * @param description attack sequence description
 * @param attack attack sequence configuration to be used in simulation
 */
case class AttackSequence(sequence: String, description: String, attack: AttackConfiguration)
