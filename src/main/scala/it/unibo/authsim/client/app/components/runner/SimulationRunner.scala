package it.unibo.authsim.client.app.components.runner

import it.unibo.authsim.client.app.simulation.AttackSimulation

import java.util.concurrent.{ExecutorService, Executors}

trait SimulationRunnerComponent:

  val simulationRunner: SimulationRunner

  /**
   * A component used to run and handle simulation executions
   */
  trait SimulationRunner:

    /**
     * Runs given simulation on a new internally-handled thread
     * @param simulation simulation to be run
     */
    def runSimulation(simulation: AttackSimulation): Unit

    /**
     * Stops currently running simulation
     */
    def stopSimulation(): Unit

  class SimulationRunnerImpl(private val executorSupplier: () => ExecutorService) extends SimulationRunner:

    private var executor = executorSupplier.apply()

    override def runSimulation(simulation: AttackSimulation): Unit =
      if executor.isShutdown then executor = executorSupplier.apply()
      executor.submit(simulation)

    override def stopSimulation(): Unit =
      executor.shutdownNow()
