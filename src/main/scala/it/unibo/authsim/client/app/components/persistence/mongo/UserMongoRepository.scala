package it.unibo.authsim.client.app.components.persistence.mongo

import de.flapdoodle.embed.mongo.{MongodProcess, MongodStarter}
import de.flapdoodle.embed.mongo.config.{MongodConfig, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import it.unibo.authsim.client.app.components.persistence.{PersistenceException, UserEntity, UserRepository}
import org.mongodb.scala.{Document, FindObservable, MongoClient, MongoCollection, Observable, Observer, SingleObservable}
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.result.InsertManyResult

import java.util.concurrent.TimeUnit
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.duration.Duration
import scala.util.{Try, Using}

trait UserMongoRepositoryComponent:

  val userMongoRepository: UserRepository

  object UserMongoRepository:

    private val mongoIp = "localhost"
    private val mongoPort = 27017
    private val connectionString = s"mongodb://$mongoIp:$mongoPort"

    private val operationTimeout = Duration(5, TimeUnit.SECONDS)

    private val databaseName = "authsim"

    private val starter = MongodStarter.getDefaultInstance;
    private val mongoExecutable = initializeDatabase()

    private def initializeDatabase(): MongodProcess =
      val mongodConfig = MongodConfig
        .builder()
        .version(Version.Main.PRODUCTION)
        .net(new Net(mongoIp, mongoPort, Network.localhostIsIPv6()))
        .build()

      val mongodExecutable = starter.prepare(mongodConfig)
      mongodExecutable.start()

  /**
   * Mongo implementation of UserRepository
   */
  class UserMongoRepository extends UserRepository :

    private val mongoClient = MongoClient()

    override def saveUsers(users: Seq[UserEntity]): Try[Unit] =
      return usingCollection[Unit]("users",
        collection =>
          val documents = users.map(user => Document("username" -> user.username, "password" -> user.password))
          val resultObservable: SingleObservable[InsertManyResult] = collection.insertMany(documents)
          val promise = Promise[Unit]
            resultObservable.subscribe(saveUsersObserverFromPromise(promise))
          Await.result(promise.future, UserMongoRepository.operationTimeout)
      )

    private def saveUsersObserverFromPromise(promise: Promise[Unit]): Observer[InsertManyResult] = new Observer[InsertManyResult] {
      def onNext(result: InsertManyResult): Unit =
        return // do nothing

      def onError(e: Throwable): Unit =
        promise.failure(e)

      def onComplete(): Unit =
        promise.success(())
    }

    override def resetUsers(): Try[Unit] =
      usingCollection("users",
        collection =>
          val dropObservable = collection.drop
          val promise = Promise[Unit]
            dropObservable.subscribe(resetUsersObservableFromPromise(promise))
          Await.result(promise.future, UserMongoRepository.operationTimeout)
      )

    private def resetUsersObservableFromPromise(promise: Promise[Unit]): Observer[Void] = new Observer[Void] {
      def onNext(result: Void): Unit =
        return // do nothing

      def onError(e: Throwable): Unit =
        promise.failure(e)

      def onComplete(): Unit =
        promise.success(())
    }

    override def retrieveUser(username: String, password: String): Try[UserEntity] =
      usingCollection("users",
        collection =>
          val findObservable = collection.find(and(equal("username", username), equal("password", password)))
          val promise = Promise[UserEntity]
          findObservable.subscribe(retrieveUserObservableFromPromise(promise))
          Await.result(promise.future, UserMongoRepository.operationTimeout)
      )

    private def retrieveUserObservableFromPromise(promise: Promise[UserEntity]): Observer[Document] = new Observer[Document] {
      override def onNext(result: Document): Unit =
        val usernameWrapper = result.get("username")
        val passwordWrapper = result.get("password")
        if usernameWrapper.isDefined && passwordWrapper.isDefined then
          val userEntity = new UserEntity(usernameWrapper.get.asString.getValue, passwordWrapper.get.asString.getValue)
          promise.success(userEntity)
        else
          promise.failure(new PersistenceException("Could not parse user from " + usernameWrapper + " " + passwordWrapper))

      override def onError(e: Throwable): Unit =
        promise.failure(e)

      override def onComplete(): Unit =
        return // do nothing
    }

    override def retrieveAllUsers(): Try[Seq[UserEntity]] =
      usingCollection("users",
        collection =>
          val retrieveUsersObservable = collection.find()
          val promise = Promise[Seq[UserEntity]]
          retrieveUsersObservable.subscribe(retrieveAllUsersObservableFromPromise(promise))
          Await.result(promise.future, UserMongoRepository.operationTimeout)
      )

    private def retrieveAllUsersObservableFromPromise(promise: Promise[Seq[UserEntity]]): Observer[Document] = new Observer[Document] {

      private var users = ListBuffer[UserEntity]()

      override def onNext(result: Document): Unit =
        val userEntity = convertDocumentToUserEntity(result)
        userEntity match
          case Some(user) => users += user
          case None => promise.failure(new PersistenceException(s"Failed to parse user from $result"))


      override def onError(e: Throwable): Unit =
        promise.failure(e)

      override def onComplete(): Unit =
        return promise.success(users.toSeq);
    }

    private def convertDocumentToUserEntity(result: Document): Option[UserEntity] =
      val usernameWrapper = result.get("username")
      val passwordWrapper = result.get("password")
      if usernameWrapper.isDefined && passwordWrapper.isDefined then
        Some(new UserEntity(usernameWrapper.get.asString.getValue, passwordWrapper.get.asString.getValue))
      else
        None

    private def usingCollection[T](collectionName: String, collectionFunction: (MongoCollection[Document] => T)): Try[T] =
      Try {
        val collection = mongoClient.getDatabase(UserMongoRepository.databaseName).getCollection(collectionName)
        collectionFunction.apply(collection)
      }