package it.unibo.authsim.client.app.components.persistence.sql

import it.unibo.authsim.client.app.components.persistence.{PersistenceException, UserEntity, UserRepository}
import it.unibo.authsim.client.app.components.config.PropertiesServiceComponent

import java.sql.{Connection, DriverManager, PreparedStatement, Statement}
import scala.util.{Failure, Success, Try, Using}

trait UserSqlRepositoryComponent:

  this: PropertiesServiceComponent =>

  val userSqlRepository: UserRepository

  object UserSqlRepository :

    private val createTableSql =
      """
        |drop table if exists users;
        |create table users(ID INT PRIMARY KEY AUTO_INCREMENT, USERNAME VARCHAR(500), PASSWORD VARCHAR(500));
      """.stripMargin

    private val saveUsersSql =
      """
        |insert into users (USERNAME, PASSWORD) values (?, ?)
      """.stripMargin

    private val resetUsersSql =
      """
        |truncate table users
       """.stripMargin

    private val selectUserSql =
      """
        |select * from users where username = ? and password = ?
      """.stripMargin

  class UserSqlRepository extends UserRepository:

    private val baseDirectory = propertiesService.databaseBasePathFolder
    private val databaseName = "my-h2-db"
    private val databaseDirectory = s"$baseDirectory/$databaseName"
    private val databaseUrl = s"jdbc:h2:$databaseDirectory"

    initializeDatabase()

    private def initializeDatabase(): Unit =
      var initializationResult = usingStatement(
        UserSqlRepository.createTableSql,
        statement => statement.executeUpdate()
      )
      initializationResult match
        case Success(_) =>
        case Failure(error) => throw new PersistenceException("Db initialization failed: " + error.getMessage)


    override def saveUsers(users: Seq[UserEntity]): Try[Unit] =
      usingStatement(
        UserSqlRepository.saveUsersSql,
        statement =>
          users.foreach(user =>
            statement.setString(1, user.username)
            statement.setString(2, user.password)

            statement.addBatch()
          )
          statement.executeBatch()
      )

    override def resetUsers(): Try[Unit] =
      usingStatement(
        UserSqlRepository.resetUsersSql,
        statement => statement.executeUpdate()
      )

    override def retrieveUser(username: String, password: String): Try[UserEntity] =
      usingStatement(
        UserSqlRepository.selectUserSql,
        statement =>
          statement.setString(1, username)
          statement.setString(2, password)
          val resultSet = statement.executeQuery()
          if resultSet.next() then
            val username = resultSet.getString("USERNAME")
            val password = resultSet.getString("PASSWORD")
            new UserEntity(username, password)
          else
            throw new PersistenceException(s"Could not retrieve user $username $password from DB")

      )

    private def usingStatement[T](sql: String, statementFunction: (PreparedStatement => T)): Try[T] =
      Using(DriverManager.getConnection(databaseUrl).prepareStatement(sql)) {
        (statement) => statementFunction.apply(statement)
      }