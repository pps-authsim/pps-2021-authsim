package it.unibo.authsim.client.app.simulation

import it.unibo.authsim.client.app.components.config.{PropertiesService, PropertiesServiceComponent}
import it.unibo.authsim.client.app.components.persistence.{UserEntity, UserRepository}
import it.unibo.authsim.client.app.components.persistence.sql.UserSqlRepositoryComponent
import it.unibo.authsim.client.app.components.testutils.PropertiesServiceStub
import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.simulation.attacks.AttackConfiguration
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.testing.DataBaseTest
import javafx.embed.swing.JFXPanel
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ListBuffer

class AttackSimulationIntegrationTest extends AnyWordSpec with UserSqlRepositoryComponent with PropertiesServiceComponent:

  override val propertiesService: PropertiesService = PropertiesServiceStub()
  override val userSqlRepository: UserRepository = UserSqlRepository()

  val jfxPanel = new JFXPanel

  "AttackSimulation" when {

    "simulation is run" should {

      "crack password" taggedAs (DataBaseTest) in {
        val simulation = attackSimulation()

        var capturedLogs = ListBuffer[String]()
        simulation.messageProperty().addListener((observable, oldValue, newValue) => capturedLogs += newValue)

        simulation.call()

        val allUsers = userSqlRepository.retrieveAllUsers().get
        assert(allUsers.sameElements(Seq(UserEntity("user", "password"))))

        val expectedLogs = Seq(
          "\nStarting an attack procedure 'GuessDefaultPassword' with 'Simple' security policy and 'Sql' credentails source for users List(user:password)...",
          "\nAttack procedure started...",
          "\nAttempts: 1",
          "\nBreached credentials: user - password",
          "\nAttack simulation completed!"
        )
        expectedLogs.foreach(log => assert(capturedLogs.contains(log)))
      }

    }

  }

  private def attackSimulation(): AttackSimulation =
    val users = Seq(User("user", "password"))
    val policy = "Simple"
    val credentialsSource = CredentialsSourceType.Sql
    val attackSequence = AttackConfiguration.GuessDefaultPassword
    new AttackSimulation(users, policy, credentialsSource, attackSequence)


