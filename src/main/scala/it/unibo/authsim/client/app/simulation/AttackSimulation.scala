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

  override def call(): Unit =
    launchAttack match
      case Success(_) => succeeded()
      case Failure(e) => printErrorMessage(s"Simulation failed: $e")

  private val launchAttack: Try[Unit] = Try {
    printInitialMessage()
    insertUsersIntoDatabase() match
      case Success(_) => buildAndExecuteAttack()
      case Failure(error) => throw new SimulationException(error.getMessage)
  }

  private def buildAndExecuteAttack(): Unit =
    val attackBuilder = makeAttack(makeUserProvider)(makeLogger)
    printAttackStarted()
    startAttack(attackBuilder)
    printAttackFinished()

  private def insertUsersIntoDatabase(): Try[Unit] =
    val userEntities = users.map(user => new UserEntity(user.username, user.password)).toList
    database.saveUsers(userEntities)

  private val makeUserProvider: UserProvider =
    val matchedAlgorithm = SecurityPolicy.Default.cryptographicAlgorithmFrom(policy)
    new RepositoryUserProvider(database, matchedAlgorithm)

  private val database: UserRepository = credentialsSource match
    case CredentialsSourceType.Sql => ComponentRegistry.userSqlRepository
    case CredentialsSourceType.Mongo => ComponentRegistry.userMongoRepository

  private val makeLogger: StatisticsConsumer =
    new StatisticsLogger(printStatistics)

  private val makeAttack: UserProvider => StatisticsConsumer => AttackBuilder =
    userProvider => logger => matchAttack(AttacksFactory(userProvider, logger))(attackSequence)

  private val matchAttack: AttacksFactory => AttackConfiguration => AttackBuilder =
    factory => attackSequence => attackSequence match
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
