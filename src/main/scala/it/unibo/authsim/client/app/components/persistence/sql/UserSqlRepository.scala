package it.unibo.authsim.client.app.components.persistence.sql

import it.unibo.authsim.client.app.components.persistence.{PersistenceException, UserEntity, UserRepository}
import it.unibo.authsim.client.app.components.config.PropertiesServiceComponent

import java.sql.{Connection, DriverManager, PreparedStatement, Statement}
import scala.util.{Failure, Success, Try, Using}

trait UserSqlRepositoryComponent:

  this: PropertiesServiceComponent =>

  val userSqlRepository: UserRepository

  object UserSqlRepository :

    val createTableSql =
      """
        |drop table users;
        |create table users(ID INT PRIMARY KEY AUTO_INCREMENT, USERNAME VARCHAR(500), PASSWORD VARCHAR(500));
      """.stripMargin

    val saveUsersSql =
      """
        |insert into users (USERNAME, PASSWORD) values (?, ?)
      """.stripMargin

    val resetUsersSql =
      """
        |truncate table users
       """.stripMargin

    val selectUserSql =
      """
        |select * from users where username = ? and password = ?
      """.stripMargin

  class UserSqlRepository extends UserRepository:

    val baseDirectory: String = propertiesService.databaseBasePathFolder
    val databaseName: String = "my-h2-db"
    val databaseDirectory: String = s"$baseDirectory/$databaseName"
    val databaseUrl: String = s"jdbc:h2:$databaseDirectory"

    initializeDatabase()

    private def initializeDatabase(): Unit =
      var initializationResult = usingStatement(
        UserSqlRepository.createTableSql,
        statement => statement.executeUpdate()
      )
      initializationResult match
        case Success(_) =>
        case Failure(error) => throw new PersistenceException("Db initialization failed: " + error.getMessage)


    def saveUsers(users: Seq[UserEntity]): Try[Unit] =
      usingStatement(
        UserSqlRepository.saveUsersSql,
        statement =>
          users.foreach(user =>
            statement.setString(1, user.username)
            statement.setString(2, user.password)

            statement.addBatch()
          )
          statement.executeUpdate()

      )

    def resetUsers(): Try[Unit] =
      usingStatement(
        UserSqlRepository.resetUsersSql,
        statement => statement.executeUpdate()
      )

    def retrieveUser(username: String, password: String): Try[UserEntity] =
      usingStatement(
        UserSqlRepository.selectUserSql,
        statement =>
          val resultSet = statement.executeQuery()
          if resultSet.next() then
            val username = resultSet.getString("USERNAME")
            val password = resultSet.getString("PASSWORD")
            new UserEntity(username, password)
          else
            throw new PersistenceException(s"Could not retrieve user $username $password from DB")

      )

    def usingStatement[T](sql: String, statementFunction: (PreparedStatement => T)): Try[T] =
      Using(DriverManager.getConnection(databaseUrl).prepareStatement(sql)) {
        (statement) => statementFunction.apply(statement)
      }