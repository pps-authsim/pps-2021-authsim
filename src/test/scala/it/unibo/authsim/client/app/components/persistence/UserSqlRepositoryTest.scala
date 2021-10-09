package it.unibo.authsim.client.app.components.persistence

import it.unibo.authsim.client.app.components.persistence.sql.UserSqlRepositoryComponent
import it.unibo.authsim.client.app.components.config.{PropertiesService, PropertiesServiceComponent}
import it.unibo.authsim.client.app.components.testutils.PropertiesServiceStub
import it.unibo.authsim.testing.DataBaseTest
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpec

import java.sql.DriverManager
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Using}

object UserSqlRepositoryTest:

  private val baseDirectory = "./data/db"
  private val databaseName = "my-h2-db"
  private val databaseDirectory= s"$baseDirectory/$databaseName"
  private val databaseUrl = s"jdbc:h2:$databaseDirectory"

class UserSqlRepositoryTest extends AnyWordSpec with BeforeAndAfterEach with UserSqlRepositoryComponent with PropertiesServiceComponent:

  override val propertiesService: PropertiesService = PropertiesServiceStub()
  override val userSqlRepository: UserRepository = UserSqlRepository()

  override def beforeEach(): Unit =
    clearDB()

  "Sql User Repository" when {

    "User is saved" should {

      "Have users saved in DB" taggedAs (DataBaseTest) in {
        val userEntity1 = new UserEntity("testUser", "1234")
        val userEntity2 = new UserEntity("anotherUser", "abcd1234")

        val insertResult = userSqlRepository.saveUsers(List(userEntity1, userEntity2))

        val snapshot = getDatabaseSnapshot()
        assert(insertResult.isSuccess)
        assert(snapshot.sameElements(List(userEntity1, userEntity2)))
      }

    }

    "Users are reset" should {

      "Have no more users in DB" taggedAs (DataBaseTest) in {
        setUpUsersInDb()

        val resetResult = userSqlRepository.resetUsers()

        val snapshot = getDatabaseSnapshot()

        assert(resetResult.isSuccess)
        assert(snapshot.sameElements(List()))
      }

    }

    "User is retrieved" should {

      "Retrieve user if present in DB" taggedAs (DataBaseTest) in {
        setUpUsersInDb()

        val retrieveResult = userSqlRepository.retrieveUser("testUser", "1234")

        assert(retrieveResult.isSuccess)
        assert(retrieveResult.get.equals(new UserEntity("testUser", "1234")))
      }

      "Retrieve nothing if not present in DB" taggedAs (DataBaseTest) in {
        setUpUsersInDb()

        val retrieveResult = userSqlRepository.retrieveUser("noUser", "pass")

        assert(retrieveResult.isFailure)
      }

      "All users are retrieved" should {

        "Retrieve all users" taggedAs (DataBaseTest) in {
          setUpUsersInDb()

          val retrieveResult = userSqlRepository.retrieveAllUsers()

          assert(retrieveResult.isSuccess)
          assert(retrieveResult.get.equals(Seq(new UserEntity("testUser", "1234"), new UserEntity("anotherUser", "abcd1234"))))
        }

        "Retrieve nothing if users not present" taggedAs (DataBaseTest) in {
          val retrieveResult = userSqlRepository.retrieveAllUsers()

          assert(retrieveResult.isSuccess)
          assert(retrieveResult.get.equals(Seq()))
        }

      }

    }

  }

  private def getDatabaseSnapshot(): Seq[UserEntity] =
    val sql = "select * from users"

    val connection = DriverManager.getConnection(UserSqlRepositoryTest.databaseUrl)
    val statement = connection.createStatement()
    val resultSet =  statement.executeQuery(sql)

    var retrievedUsers = ListBuffer[UserEntity]()

    while resultSet.next() do
      val username = resultSet.getString("USERNAME")
      val password = resultSet.getString("PASSWORD")
      retrievedUsers += new UserEntity(username, password)

    retrievedUsers.toSeq

  private def setUpUsersInDb(): Unit =
    val userEntity1 = new UserEntity("testUser", "1234")
    val userEntity2 = new UserEntity("anotherUser", "abcd1234")

    insertUsersIntoDB(List(userEntity1, userEntity2))

  private def insertUsersIntoDB(users: Seq[UserEntity]): Unit =
    users.foreach(user =>
      val username = user.username
      val password = user.password
      val sql = s"insert into users (USERNAME, PASSWORD) values ('$username', '$password')"
      val connection = DriverManager.getConnection(UserSqlRepositoryTest.databaseUrl)
      val statement = connection.createStatement()
      statement.executeUpdate(sql)
    )

  private def clearDB(): Unit =
    val sql = "truncate table users"
    val connection = DriverManager.getConnection(UserSqlRepositoryTest.databaseUrl)
    val statement = connection.createStatement()
    statement.executeUpdate(sql)