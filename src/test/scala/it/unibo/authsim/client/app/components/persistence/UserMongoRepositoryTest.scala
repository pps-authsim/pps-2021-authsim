package it.unibo.authsim.client.app.components.persistence

import de.flapdoodle.embed.mongo.{MongodProcess, MongodStarter}
import de.flapdoodle.embed.mongo.config.{MongodConfig, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import it.unibo.authsim.client.app.components.config.{PropertiesService, PropertiesServiceComponent}
import it.unibo.authsim.client.app.components.persistence.mongo.UserMongoRepositoryComponent
import it.unibo.authsim.client.app.components.testutils.PropertiesServiceStub
import org.mongodb.scala.{Document, MongoClient, Observer, SingleObservable}
import org.mongodb.scala.model.Filters.{and, equal}
import org.mongodb.scala.result.InsertManyResult
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatest.wordspec.AnyWordSpec

import java.util.concurrent.TimeUnit
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

object UserMongoRepositoryTest:
  private val operationTimeout = Duration(5, TimeUnit.SECONDS)

  private val databaseName = "authsim"
  private val collectionName = "users"

class UserMongoRepositoryTest extends AnyWordSpec with BeforeAndAfterAll with BeforeAndAfterEach with UserMongoRepositoryComponent with PropertiesServiceComponent:

  override val propertiesService: PropertiesService = PropertiesServiceStub()
  override val userMongoRepository: UserRepository = UserMongoRepository()

  private val mongoClient = MongoClient()

  override def beforeEach(): Unit =
    val collection = mongoClient.getDatabase(UserMongoRepositoryTest.databaseName).getCollection(UserMongoRepositoryTest.collectionName)
    val promise = Promise[Unit]
    val observable = collection.drop()
    observable.subscribe(new Observer[Void] {
      def onNext(result: Void): Unit =
        return // do nothing

      def onError(e: Throwable): Unit =
        promise.failure(e)

      def onComplete(): Unit =
        promise.success(())
    })
    Await.result(promise.future, UserMongoRepositoryTest.operationTimeout)

  "Mongo User Repository" when {

    "User is saved" should {

      "Have users saved in DB" in {
        val userEntity1 = new UserEntity("testUser", "1234")
        val userEntity2 = new UserEntity("anotherUser", "abcd1234")

        val insertResult = userMongoRepository.saveUsers(List(userEntity1, userEntity2))

        // FIXME runs well first time, then gets dirtied, then stays the same
        val snapshot = getDatabaseSnapshot()
        assert(insertResult.isSuccess)
        assert(snapshot.sameElements(List(userEntity1, userEntity2)))
      }

    }

    "Users are reset" should {

      "Have no more users in DB" in {
        setUpUsersInDb()

        val resetResult = userMongoRepository.resetUsers()

        val snapshot = getDatabaseSnapshot()

        assert(resetResult.isSuccess)
        assert(snapshot.sameElements(List()))
      }

    }

    "User is retrieved" should {

      "Retrieve user if present in DB" in {
        setUpUsersInDb()

        val retrieveResult = userMongoRepository.retrieveUser("testUser", "1234")

        assert(retrieveResult.isSuccess)
        assert(retrieveResult.get.equals(new UserEntity("testUser", "1234")))
      }

      "Retrieve nothing if not present in DB" in {
        val retrieveResult = userMongoRepository.retrieveUser("noUser", "pass")

        assert(retrieveResult.isFailure)
      }

    }

  }

  private def getDatabaseSnapshot(): Seq[UserEntity] =
    val collection = mongoClient.getDatabase(UserMongoRepositoryTest.databaseName).getCollection(UserMongoRepositoryTest.collectionName)
    val findObservable = collection.find()
    val promise = Promise[Seq[UserEntity]]
    findObservable.subscribe(new Observer[Document] {

      private val users = ListBuffer[UserEntity]()

      override def onNext(result: Document): Unit =
        val usernameWrapper = result.get("username")
        val passwordWrapper = result.get("password")

        if usernameWrapper.isDefined && passwordWrapper.isDefined then
          val userEntity = new UserEntity(usernameWrapper.get.asString.getValue, passwordWrapper.get.asString.getValue)
          users += userEntity

      override def onError(e: Throwable): Unit =
        promise.failure(e)

      override def onComplete(): Unit =
        promise.success(users.toSeq)

    })
    Await.result(promise.future, UserMongoRepositoryTest.operationTimeout)

  private def setUpUsersInDb(): Unit =
    val userEntity1 = new UserEntity("testUser", "1234")
    val userEntity2 = new UserEntity("anotherUser", "abcd1234")

    insertUsersIntoDB(List(userEntity1, userEntity2))

  private def insertUsersIntoDB(users: Seq[UserEntity]): Unit =
    val collection = mongoClient.getDatabase(UserMongoRepositoryTest.databaseName).getCollection(UserMongoRepositoryTest.collectionName)
    val documents = users.map(user => Document("username" -> user.username, "password" -> user.password))
    val resultObservable: SingleObservable[InsertManyResult] = collection.insertMany(documents)
    val promise = Promise[Unit]
    resultObservable.subscribe(new Observer[InsertManyResult] {
      def onNext(result: InsertManyResult): Unit =
        promise.success(())

      def onError(e: Throwable): Unit =
        promise.failure(e)

      def onComplete(): Unit =
        return // do nothing
    })
    Await.result(promise.future, UserMongoRepositoryTest.operationTimeout)

