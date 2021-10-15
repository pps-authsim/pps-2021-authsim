package it.unibo.authsim.client.app.simulation

import it.unibo.authsim.client.app.components.persistence.{UserEntity, UserRepository}
import it.unibo.authsim.client.app.components.registry.ComponentRegistry
import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityPolicy}
import it.unibo.authsim.client.app.simulation.exception.SimulationException
import it.unibo.authsim.client.app.simulation.integration.StatisticsLogger
import it.unibo.authsim.library.UserProvider
import it.unibo.authsim.library.user.model.{User, UserInformation}
import javafx.concurrent.Task
import it.unibo.authsim.client.app.simulation.attacks.{AttackConfiguration, AttacksFactory}

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}
import scala.concurrent.duration.{Duration, HOURS}
import it.unibo.authsim.client.app.simulation.attacks.AttackConfiguration
import it.unibo.authsim.client.app.simulation.integration.RepositoryUserProvider
import it.unibo.authsim.library.attack.builders.AttackBuilder
import it.unibo.authsim.library.attack.statistics.Statistics
import it.unibo.authsim.library.consumers.StatisticsConsumer

class AttackSimulation(
                        private val users: Seq[User],
                        private val policy: String,
                        private val credentialsSource: CredentialsSourceType,
                        private val attackSequence: AttackConfiguration
                      ) extends Task[Unit]:

  private val database: UserRepository = credentialsSource match
    case CredentialsSourceType.Sql => ComponentRegistry.userSqlRepository
    case CredentialsSourceType.Mongo => ComponentRegistry.userMongoRepository

  override def call(): Unit =
    try
      printInitialMessage()
      insertUsersIntoDatabase()
      val userProvider = makeUserProvider()
      val logger = makeLogger()
      val attackBuilder = makeAttack(userProvider, logger)
      printAttackStarted()
      startAttack(attackBuilder)
      printAttackFinished()
      succeeded()
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
    attackSequence match
      case AttackConfiguration.BruteForceAll => factory.bruteForceAll()
      case AttackConfiguration.BruteForceLetters => factory.bruteForceLetters()
      case AttackConfiguration.BruteForceLowers => factory.bruteForceLowers()
      case AttackConfiguration.DictionaryMostCommonPasswords => factory.dictionaryMostCommonPasswords()
      case AttackConfiguration.GuessDefaultPassword => factory.guessDefaultPassword()

  private def startAttack(attackBuilder: AttackBuilder): Unit =
    if attackBuilder.getTimeout().isEmpty then attackBuilder timeout Duration(1, HOURS)
    attackBuilder.executeNow()

  private val printStatistics: (Statistics => Unit) = (statistics: Statistics) =>
    if statistics.timedOut then logMessage("The attack timed out: the following statistics could be incomplete.")
    logMessage("Attempts: " + statistics.attempts)
    logMessage("Elapsed time: " + statistics.elapsedTime.toMillis + " ms")
    logMessage("Breached credentials: " + statistics.successfulBreaches.map(u => u.username + " - " + u.password).reduceOption((u1, u2) => u1 + "\n" + u2).getOrElse("None"))

  private def printAttackStarted(): Unit =
    logMessage("Attack procedure started...")

  private def printErrorMessage(message:String): Unit =
    logMessage(s"Could not launch attack, an error has occured: $message...")

  private def printInitialMessage(): Unit =
    val initialText =  s"Starting an attack procedure '$attackSequence' with '$policy' security policy and '$credentialsSource' credentails source for users ${users.map(u => u.username + ":" + u.password).toList}..."
    logMessage(initialText)

  private def printAttackFinished(): Unit =
    val text = "Attack simulation completed!"
    logMessage(text)

  private def logMessage(message: String): Unit =
    updateMessage(s"\n$message")
    Thread.sleep(500)
