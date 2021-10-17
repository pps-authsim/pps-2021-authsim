package it.unibo.authsim.client.app.components.config

import java.io.{ByteArrayInputStream, InputStream}
import java.util.Properties
import scala.io.Source

/**
 * Serivice responsible for reading and exposing application properties
 * @param inputStream input stream of the readable java properties. Importantly, this stream will be closed after reading its contents
 */
trait PropertiesService:

  /**
   * Path to the folder containing path to the in-memory database folder
   */
  val databaseBasePathFolder: String

object PropertiesServiceComponent:
  private def databaseBasePathFolderKey = "db.base.path"

trait PropertiesServiceComponent:

  val propertiesService: PropertiesService
  
  class PropertiesServiceImpl(source: Source) extends PropertiesService:

    private val lines = source.getLines.reduce((a, b) => a + "\n" + b)
    private val inputStream = new ByteArrayInputStream(lines.getBytes)

    private val properties = loadPropertiesFromInputStream(inputStream)

    override val databaseBasePathFolder = getPropertyOrFail(PropertiesServiceComponent.databaseBasePathFolderKey)

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