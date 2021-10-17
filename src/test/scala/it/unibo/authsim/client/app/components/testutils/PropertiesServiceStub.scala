package it.unibo.authsim.client.app.components.testutils

import it.unibo.authsim.client.app.components.config.PropertiesService
import it.unibo.authsim.client.app.components.persistence.UserSqlRepositoryTest

object PropertiesServiceStub:
  private val baseDirectory = "./data/db"

class PropertiesServiceStub extends PropertiesService:

  override val databaseBasePathFolder = PropertiesServiceStub.baseDirectory
