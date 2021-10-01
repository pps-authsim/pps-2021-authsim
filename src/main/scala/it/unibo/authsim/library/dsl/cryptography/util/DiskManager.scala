package it.unibo.authsim.library.dsl.cryptography.util

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream, PrintWriter}
import scala.util.{Random, Try}
/**
 * Utility object used for disk operation management
 */
object DiskManager:

  import java.io.{File, FileInputStream, FileOutputStream, ObjectOutputStream, PrintWriter}
  import scala.io.Source

  /**
   * Method that checks id a file does exists
   *
   * @param fileName      name of the file to check
   * @return              true if the file already exists or false if it does not
   */
  def isExisting(fileName: String): Boolean =
    new File(fileName).exists()

  /**
   * Method that save an object on a file and print a log string in case of failure
   *
   * @param obj            instance of the object to be saved on the file
   * @param fileName       name of the file on which the object should be saved
   * @tparam T             type of the object to be saved
   */
  def saveObject[T](obj: T, fileName: String): Unit =
    val oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)))
    Try {
      oos.writeObject(obj)
      }.toEither match {
        case Left(error) =>
          println("Error in saving the file")
        case Right(_) =>
          oos.close
      }

  /**
   * Method that load an object from an existing file, or print a log string in case of failure
   *
   * @param fileName        name of the file that should contain the object
   * @tparam T              type of the object
   * @return                None in case of failure, or an optional of the object loaded
   */
  def loadObject[T](fileName: String): Option[T] =
    val objectInputStream = new ObjectInputStream(new FileInputStream(new File(fileName)))
    var res: Option[T]= None
    Try {
      res=Some(objectInputStream.readObject.asInstanceOf[T])
      }.toEither match {
        case Left(error) =>
          println("Error in reading the file")
          None
        case Right(_) =>
          objectInputStream.close
          res
    }
