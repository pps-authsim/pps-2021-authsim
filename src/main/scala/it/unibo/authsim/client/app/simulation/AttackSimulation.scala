package it.unibo.authsim.client.app.simulation

import it.unibo.authsim.client.app.components.persistence.{UserEntity, UserRepository}
import it.unibo.authsim.client.app.components.registry.ComponentRegistry
import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityPolicy}
import it.unibo.authsim.client.app.simulation.exception.SimulationException
import it.unibo.authsim.client.app.simulation.provider.RepositoryUserProvider
import it.unibo.authsim.library.dsl.UserProvider

import it.unibo.authsim.library.user.model.{User, UserInformation}
import javafx.concurrent.Task

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

class AttackSimulation(
                        val users: ListBuffer[User],
                        val policy: String,
                        val credentialsSource: CredentialsSourceType,
                        val attackSequence: String
                      ) extends Task[Unit]:

  private val database: UserRepository = credentialsSource match
    case CredentialsSourceType.Sql => ComponentRegistry.userSqlRepository
    case CredentialsSourceType.Mongo => ComponentRegistry.userMongoRepository

  override def call(): Unit =
    printInitialMessage()
    try
      val userProvider = makeUserProvider()
      insertUsersIntoDatabase()
      printAttackFinished()
    catch
      case e: SimulationException => printErrorMessage(e.message)

  private def makeUserProvider(): UserProvider =
    val matchedPolicy = SecurityPolicy.Default.all.find(policyToMatch => policyToMatch.policy.equals(policy))
    matchedPolicy match
      case Some(value) => new RepositoryUserProvider(database, None)
      case None => throw new SimulationException("Could not match provided policy with library policies")

  private def insertUsersIntoDatabase(): Unit =
    val userEntities = users.map(user => new UserEntity(user.username, user.password)).toList

    database.saveUsers(userEntities) match
      case Success(_) => // do nothing
      case Failure(error) => throw new SimulationException(error.getMessage)

  private def printErrorMessage(message:String) =
    logMessage(s"Could not launch attack, an error has occured: $message...")

  private def printInitialMessage(): Unit =
    val initialText =  s"Starting an attack procedure '$attackSequence' with '$policy' security policy and '$credentialsSource' credentails source for users $users..."
    logMessage(initialText)

  private def printAttackFinished(): Unit =
    val text = "Attack simulation completed!"
    logMessage(text)

  private def logMessage(message: String) =
    updateMessage(s"\n$message")
