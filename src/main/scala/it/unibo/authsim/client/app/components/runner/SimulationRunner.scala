package it.unibo.authsim.client.app.components.runner

import it.unibo.authsim.client.app.simulation.AttackSimulation


trait SimulationRunnerComponent:

  val simulationRunner: SimulationRunner

  trait SimulationRunner:

    def runSimulation(simulation: AttackSimulation): Unit

  class SimulationRunnerImpl extends SimulationRunner:

    def runSimulation(simulation: AttackSimulation): Unit =
      new Thread(simulation).start()
