package it.unibo.authsim.client.app.mvvm.common

enum CredentialsSourceType(val description: String):

  case Sql extends CredentialsSourceType("""
                                           |Simple in-memory SQL database that will save users in a (username: VARCHAR, password: VARCHAR) format in a table.
                                           |Implemented with H2 in-memory database
                                           |""".stripMargin)

  case Mongo extends CredentialsSourceType("""
                                             |Simple in-memory MongoDb databse that will save users in a 'users' collection as { username: "foo", password: "bar" }.
                                             |Implemented with flapdoodle in-memory mongoDB database
                                             |""".stripMargin)
