package it.unibo.authsim.client.app.components.persistence.mongo

import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.{MongodConfig, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import it.unibo.authsim.client.app.components.config.PropertiesServiceComponent
import it.unibo.authsim.client.app.components.persistence.{PersistenceException, UserEntity, UserRepository}
import org.mongodb.scala.{Document, FindObservable, MongoClient, MongoCollection, Observable, Observer, SingleObservable}
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.result.InsertManyResult

import java.util.concurrent.TimeUnit
import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.duration.Duration
import scala.util.{Try, Using}

trait UserMongoRepositoryComponent:

  this: PropertiesServiceComponent =>

  val userMongoRepository: UserRepository

  class UserMongoRepository extends UserRepository :

    private val mongoIp = "localhost"
    private val mongoPort = 27017
    private val connectionString = s"mongodb://$mongoIp:$mongoPort"

    private val operationTimeout = Duration(5, TimeUnit.SECONDS)

    private val databaseName = "authsim"

    private val mongoClient = initializeDatabase()

    def saveUsers(users: Seq[UserEntity]): Try[Unit] =
      return usingCollection[Unit]("users",
        collection =>
          val documents = users.map(user => Document("username" -> user.username, "password" -> user.password))
          val resultObservable: SingleObservable[InsertManyResult] = collection.insertMany(documents)
          val promise = Promise[Unit]
            resultObservable.subscribe(saveUsersObserverFromPromise(promise))
          Await.result(promise.future, operationTimeout)
      )

    private def saveUsersObserverFromPromise(promise: Promise[Unit]): Observer[InsertManyResult] = new Observer[InsertManyResult] {
      def onNext(result: InsertManyResult): Unit =
        promise.success(())

      def onError(e: Throwable): Unit =
        promise.failure(e)

      def onComplete(): Unit =
        return // do nothing
    }

    def resetUsers(): Try[Unit] =
      usingCollection("users",
        collection =>
          val dropObservable = collection.drop
          val promise = Promise[Unit]
            dropObservable.subscribe(resetUsersObservableFromPromise(promise))
          Await.result(promise.future, operationTimeout)
      )

    private def resetUsersObservableFromPromise(promise: Promise[Unit]): Observer[Void] = new Observer[Void] {
      def onNext(result: Void): Unit =
        promise.success(())

      def onError(e: Throwable): Unit =
        promise.failure(e)

      def onComplete(): Unit =
        return // do nothing
    }

    def retrieveUser(username: String, password: String): Try[UserEntity] =
      usingCollection("users",
        collection =>
          val findObservable = collection.find(and(equal("username", username), equal("password", password)))
          val promise = Promise[UserEntity]
          findObservable.subscribe(retrieveUserObservableFromPromise(promise))
          Await.result(promise.future, operationTimeout)
      )

    private def retrieveUserObservableFromPromise(promise: Promise[UserEntity]): Observer[Document] = new Observer[Document] {
      override def onNext(result: Document): Unit =
        val username = result.get("username").asInstanceOf[String]
        val password = result.get("password").asInstanceOf[String]
        val userEntity = new UserEntity(username, password)
        promise.success(userEntity)

      override def onError(e: Throwable): Unit =
        promise.failure(e)

      override def onComplete(): Unit =
        return // do nothing
    }

    private def usingCollection[T](collectionName: String, collectionFunction: (MongoCollection[Document] => T)): Try[T] =
      Try {
        val collection = mongoClient.getDatabase(databaseName).getCollection(collectionName)
        collectionFunction.apply(collection)
      }

    private def initializeDatabase(): MongoClient =
      val mongodConfig = MongodConfig
        .builder()
        .version(Version.Main.PRODUCTION)
        .net(new Net(mongoIp, mongoPort, Network.localhostIsIPv6()))
        .build()

      val starter = MongodStarter.getDefaultInstance;
      val mongodExecutable = starter.prepare(mongodConfig)
      mongodExecutable.start()

      MongoClient()
