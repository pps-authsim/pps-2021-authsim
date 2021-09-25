package it.unibo.authsim.client.app.components.config

import java.io.{ByteArrayInputStream, InputStream}
import java.util.Properties
import scala.io.Source

trait PropertiesService:

  val databaseBasePathFolder: String


trait PropertiesServiceComponent:

  val propertiesService: PropertiesService

  /**
   * Serivice responsible for reading and exposing application properties
   * @param inputStream input stream of the readable java properties. Importantly, this stream will be closed after reading its contents
   */
  class PropertiesServiceImpl(source: Source) extends PropertiesService:

    val lines = source.getLines.reduce((a, b) => a + "\n" + b)
    val inputStream = new ByteArrayInputStream(lines.getBytes)

    val properties = loadPropertiesFromInputStream(inputStream)

    override val databaseBasePathFolder = getPropertyOrFail("db.base.path")

    private def loadPropertiesFromInputStream(inputStream: InputStream): Properties =
      val properties = new Properties()
      properties.load(inputStream)
      inputStream.close()
      properties

    private def getPropertyOrFail(key: String): String =
      val value = Option[String](properties.getProperty(key))
      if value.nonEmpty then
        value.get
      else
        throw new IllegalArgumentException(s"Could not load property for key $key")