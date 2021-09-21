package it.unibo.authsim.components.persistence.mongo

import it.unibo.authsim.components.config.PropertiesServiceComponent
import it.unibo.authsim.components.persistence.{UserEntity, UserRepository}

trait UserMongoRepositoryComponent:

  this: PropertiesServiceComponent =>

  val userMongoRepository: UserRepository

  class UserMongoRepository extends UserRepository:

    def saveUsers(users: Seq[UserEntity]): Unit = ???

    def resetUsers(): Unit = ???

    def retrieveUser(username: String, password: String): Option[UserEntity] = ???