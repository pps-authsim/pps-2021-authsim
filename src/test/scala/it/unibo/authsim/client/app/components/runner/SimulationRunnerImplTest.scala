package it.unibo.authsim.client.app.components.runner

import it.unibo.authsim.client.app.simulation.AttackSimulation
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import java.util.concurrent.ExecutorService
import org.mockito.Mockito.verify
import it.unibo.authsim.client.app.components.runner.SimulationRunnerComponent
import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.security.CredentialsSource
import it.unibo.authsim.client.app.simulation.attacks.AttackConfiguration
import it.unibo.authsim.library.user.model.User
import org.scalatest.BeforeAndAfterEach
import org.mockito.Mockito.reset

class SimulationRunnerImplTest extends AnyWordSpec with SimulationRunnerComponent with BeforeAndAfterEach with MockitoSugar:

  val mockExecutorService = MockitoSugar.mock[ExecutorService]
  override val simulationRunner: SimulationRunner = new SimulationRunnerImpl(mockExecutorService)

  override def beforeEach(): Unit =
    reset(mockExecutorService)

  "Simulation Runner" when {

    "simulation is run" should {

      "Tun task" in {
        val simulation = attackSimultaion()
        simulationRunner.runSimulation(simulation)

        verify(mockExecutorService).submit(simulation)
      }

    }

    "simulation is stopped" should {

      "Stop task" in {
        simulationRunner.stopSimulation()

        verify(mockExecutorService).shutdownNow()
      }

    }

  }

  private def attackSimultaion(): AttackSimulation =
    val users = Seq(User("user", "pass"))
    val policy = "policy"
    val credentialsSource = CredentialsSourceType.Sql
    val attackSequence = AttackConfiguration.BruteForceAll
    return new AttackSimulation(users, policy, credentialsSource, attackSequence)
