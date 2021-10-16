package it.unibo.authsim.client.app.simulation.exception

/**
 * An exception respresenting an error during a simulation execution
 * @param message error message
 */
case class SimulationException(message: String) extends Exception(message)
