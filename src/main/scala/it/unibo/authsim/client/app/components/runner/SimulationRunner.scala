package it.unibo.authsim.client.app.components.runner

import it.unibo.authsim.client.app.simulation.AttackSimulation

import java.util.concurrent.{ExecutorService, Executors}


trait SimulationRunnerComponent:

  val simulationRunner: SimulationRunner

  trait SimulationRunner:

    def runSimulation(simulation: AttackSimulation): Unit

    def stopSimulation(): Unit

  class SimulationRunnerImpl extends SimulationRunner:

    var executor: ExecutorService = Executors.newSingleThreadExecutor()

    override def runSimulation(simulation: AttackSimulation): Unit =
      if executor.isShutdown then executor = Executors.newSingleThreadExecutor()
      executor.submit(simulation)

    override def stopSimulation(): Unit =
      executor.shutdownNow()
