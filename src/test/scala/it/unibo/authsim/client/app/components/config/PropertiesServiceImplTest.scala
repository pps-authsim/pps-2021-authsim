package it.unibo.authsim.client.app.components.config

import it.unibo.authsim.client.app.components.config.{PropertiesService, PropertiesServiceComponent}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

object PropertiesServiceImplTest:

  private val expectedDatabaseBasePath = "/foo/bar"

  private val propertiesMock =
    s"""
      |db.base.path=$expectedDatabaseBasePath
      |""".stripMargin

class PropertiesServiceImplTest extends AnyWordSpec with PropertiesServiceComponent:

  override val propertiesService: PropertiesService = testPropertiesService()

  def testPropertiesService(): PropertiesService =
    val propertySource = Source.fromString(PropertiesServiceImplTest.propertiesMock)
    new PropertiesServiceImpl(propertySource)

  "Properties Service" when {
    "Properties are loaded from source" should {
      "Have database base path" in {
        val parsedBasePath = propertiesService.databaseBasePathFolder

        assert(PropertiesServiceImplTest.expectedDatabaseBasePath.equals(parsedBasePath))
      }
    }
  }
