package it.unibo.authsim.components.registry

import it.unibo.authsim.components.config.{PropertiesService, PropertiesServiceComponent}
import it.unibo.authsim.components.persistence.UserRepository
import it.unibo.authsim.components.persistence.sql.UserSqlRepositoryComponent
import it.unibo.authsim.components.persistence.mongo.UserMongoRepositoryComponent

import java.io.FileInputStream

/**
 * An idiomatic implementation of the Dependency Injection pattern via the "Cake Pattern" from Scalable Component Abstractions
 */
object ComponentRegistry extends UserMongoRepositoryComponent
  with UserSqlRepositoryComponent
  with PropertiesServiceComponent:

  override val propertiesService: PropertiesService = new PropertiesServiceImpl(new FileInputStream("./src/main/scala/application.properties"))

  override val userSqlRepository: UserRepository = new UserSqlRepository

  override val userMongoRepository: UserRepository = new UserMongoRepository