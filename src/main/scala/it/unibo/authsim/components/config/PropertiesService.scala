package it.unibo.authsim.components.config

import java.io.InputStream
import java.util.Properties

trait PropertiesService:

  val databaseBasePathFolder: String


trait PropertiesServiceComponent:

  val propertiesService: PropertiesService

  /**
   * Serivice responsible for reading and exposing application properties
   * @param inputStream input stream of the readable java properties. Importantly, this stream will be closed after reading its contents
   */
  class PropertiesServiceImpl(inputStream: InputStream) extends PropertiesService:

    val properties = loadPropertiesFromInputStream(inputStream)

    override val databaseBasePathFolder = getPropertyOrFail("db.base.path")

    private def loadPropertiesFromInputStream(inputStream: InputStream): Properties =
      val properties = new Properties()
      properties.load(inputStream)
      inputStream.close()
      properties

    private def getPropertyOrFail(key: String): String =
      val value = properties.getProperty(key)
      if value == null then
        value
      else
        throw new IllegalArgumentException(s"Could not load property for key $key")