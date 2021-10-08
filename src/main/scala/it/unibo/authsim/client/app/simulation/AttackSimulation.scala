package it.unibo.authsim.client.app.simulation

import it.unibo.authsim.client.app.components.persistence.{UserEntity, UserRepository}
import it.unibo.authsim.client.app.components.registry.ComponentRegistry
import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityPolicy}
import it.unibo.authsim.client.app.simulation.exception.SimulationException
import it.unibo.authsim.client.app.simulation.provider.RepositoryUserProvider
import it.unibo.authsim.library.dsl.UserProvider
import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer
import it.unibo.authsim.library.user.model.{User, UserInformation}
import javafx.concurrent.Task

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}
import it.unibo.authsim.library.dsl.attack.builders.AttackBuilder

import scala.concurrent.duration.Duration

object AttackSimulation:
  private val DEFAULT_TIMEOUT = Duration.apply(15, scala.concurrent.duration.MINUTES)

class AttackSimulation(
                        private val users: ListBuffer[User],
                        private val policy: String,
                        private val credentialsSource: CredentialsSourceType,
                        private val attackSequence: String
                      ) extends Task[Unit]:

  private val database: UserRepository = credentialsSource match
    case CredentialsSourceType.Sql => ComponentRegistry.userSqlRepository
    case CredentialsSourceType.Mongo => ComponentRegistry.userMongoRepository

  override def call(): Unit =
    printInitialMessage()
    try
      insertUsersIntoDatabase()
      val userProvider = makeUserProvider()
      val logger = makeLogger()
      val attackBuilder = makeAttack(userProvider, logger)
      printAttackStarted()
      startAttack(attackBuilder)
      printAttackFinished()
    catch
      case e: SimulationException => printErrorMessage(e.message)

  private def insertUsersIntoDatabase(): Unit =
    val userEntities = users.map(user => new UserEntity(user.username, user.password)).toList

    database.saveUsers(userEntities) match
      case Success(_) => // do nothing
      case Failure(error) => throw new SimulationException(error.getMessage)

  private def makeUserProvider(): UserProvider =
    val matchedAlgorithm = SecurityPolicy.Default.cryptographicAlgorithmFrom(policy)
    new RepositoryUserProvider(database, matchedAlgorithm)

  private def makeLogger(): StatisticsConsumer =
    new StatisticsLogger(printStatistics)

  private def makeAttack(userProvider: UserProvider, logger: StatisticsConsumer): AttackBuilder =
    val factory = AttacksFactory(userProvider, logger)
    //TODO pattern match to determine attack to build
    factory.bruteForceSimple()

  private def startAttack(attackBuilder: AttackBuilder): Unit =
    attackBuilder.timeout(AttackSimulation.DEFAULT_TIMEOUT)
    attackBuilder.executeNow()

  private val printStatistics: (Statistics => Unit) = (statistics: Statistics) =>
    logMessage("Attempts: " + statistics.attempts)
    logMessage("Elapsed time: " + statistics.elapsedTime.toMillis + " ms")
    logMessage("Breached credentials: " + statistics.successfulBreaches.map(u => u.username + " - " + u.password).reduce((u1, u2) => u1 + "\n" + u2))

  private def printAttackStarted(): Unit =
    logMessage("Attack procedure started...")

  private def printErrorMessage(message:String): Unit =
    logMessage(s"Could not launch attack, an error has occured: $message...")

  private def printInitialMessage(): Unit =
    val initialText =  s"Starting an attack procedure '$attackSequence' with '$policy' security policy and '$credentialsSource' credentails source for users $users..."
    logMessage(initialText)

  private def printAttackFinished(): Unit =
    val text = "Attack simulation completed!"
    logMessage(text)

  private def logMessage(message: String): Unit =
    updateMessage(s"\n$message")
