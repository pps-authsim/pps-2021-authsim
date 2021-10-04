package it.unibo.authsim.client.app.simulation

import it.unibo.authsim.client.app.components.persistence.{UserEntity, UserRepository}
import it.unibo.authsim.client.app.components.registry.ComponentRegistry
import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityPolicy}
import it.unibo.authsim.client.app.simulation.exception.SimulationException
import it.unibo.authsim.library.user.model.User
import javafx.concurrent.Task

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

class AttackSimulation(
                        val users: ListBuffer[User],
                        val policy: String,
                        val credentialsSource: CredentialsSourceType,
                        val attackSequence: String
                      ) extends Task[Unit]:

  override def call(): Unit =
    printInitialMessage()
    // todo make pipeline with failure catching
    var inserUsersResult = insertUsersIntoDatabase()
    inserUsersResult match
      case Failure(error) => printErrorMessage(error.getMessage)
      case Success(value) => // TODO continue pipeline with match


  def printInitialMessage(): Unit =
    val initialText =  s"Starting an attack procedure '$attackSequence' with '$policy' security policy and '$credentialsSource' credentails source for users $users"
    updateMessage(initialText)

  def insertUsersIntoDatabase(): Try[Unit] = Try {
    val userEntities = users.map(user => new UserEntity(user.username, user.password)).toList

    val database: UserRepository = credentialsSource match
      case CredentialsSourceType.Sql => ComponentRegistry.userSqlRepository
      case CredentialsSourceType.Mongo => ComponentRegistry.userMongoRepository

    var operationResult = database.saveUsers(userEntities)
    operationResult match
      case Success(_) =>
      case Failure(error) => throw new SimulationException(error.getMessage)

  }

  def printErrorMessage(message:String) =
    updateMessage(s"\nCould not launch attack, an error has occured: $message")

