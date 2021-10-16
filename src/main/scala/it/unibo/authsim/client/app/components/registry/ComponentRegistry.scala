package it.unibo.authsim.client.app.components.registry

import it.unibo.authsim.client.app.components.config.{PropertiesService, PropertiesServiceComponent}
import it.unibo.authsim.client.app.components.persistence.UserRepository
import it.unibo.authsim.client.app.components.persistence.sql.UserSqlRepositoryComponent
import it.unibo.authsim.client.app.components.persistence.mongo.UserMongoRepositoryComponent
import it.unibo.authsim.client.app.components.runner.SimulationRunnerComponent

import java.io.FileInputStream
import java.util.concurrent.Executors
import scala.io.Source

/**
 * An idiomatic implementation of the Dependency Injection pattern via the "Cake Pattern" from Scalable Component Abstractions paper
 */
object ComponentRegistry extends UserMongoRepositoryComponent
  with UserSqlRepositoryComponent
  with PropertiesServiceComponent
  with SimulationRunnerComponent:

  override val propertiesService: PropertiesService = new PropertiesServiceImpl(Source.fromResource("application.properties"))

  override val userSqlRepository: UserRepository = new UserSqlRepository

  override val userMongoRepository: UserRepository = new UserMongoRepository

  override val simulationRunner: SimulationRunner = new SimulationRunnerImpl(() => Executors.newSingleThreadExecutor())