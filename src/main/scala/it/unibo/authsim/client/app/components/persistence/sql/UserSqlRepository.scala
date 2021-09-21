package it.unibo.authsim.client.app.components.persistence.sql

import it.unibo.authsim.client.app.components.persistence.{UserEntity, UserRepository}
import it.unibo.authsim.client.app.components.config.PropertiesServiceComponent

import java.sql.{Connection, DriverManager, PreparedStatement, Statement}
import scala.util.Using

trait UserSqlRepositoryComponent:

  this: PropertiesServiceComponent =>

  val userSqlRepository: UserRepository

  object UserSqlRepository :

    val createTableSql =
      """
        |create table users(ID INT PRIMARY KEY, USERNAME VARCHAR(500), PASSWORD VARCHAR(500));
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
      usingStatement(
        UserSqlRepository.createTableSql,
        statement => statement.executeUpdate(UserSqlRepository.createTableSql)
      )

    def saveUsers(users: Seq[UserEntity]): Unit =
      usingStatement(
        UserSqlRepository.saveUsersSql,
        statement => {
          users.foreach(user => {
            statement.setString(1, user.username)
            statement.setString(2, user.password)

            statement.addBatch()
          })
          statement.executeUpdate()
        }
      )

    def resetUsers(): Unit =
      usingStatement(
        UserSqlRepository.resetUsersSql,
        statement => statement.executeUpdate()
      )

    def retrieveUser(username: String, password: String): Option[UserEntity] =
      var result: Option[UserEntity] = Option.empty
      usingStatement(
        UserSqlRepository.selectUserSql,
        statement => {
          val resultSet = statement.executeQuery()
          if resultSet.next() then
            val username = resultSet.getString("USERNAME")
            val password = resultSet.getString("PASSWORD")
            result = Option[UserEntity](new UserEntity(username, password))
        }
      )
      result

    def usingStatement[T](sql: String, statementConsumer: (PreparedStatement => T)): Unit =
      Using(DriverManager.getConnection(databaseUrl)) {
        (connection) => Using(connection.prepareStatement(sql)){
          (statement) => statementConsumer.apply(statement)
        }
      }