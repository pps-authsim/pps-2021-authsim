package it.unibo.authsim.library.dsl.cryptography.util

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream, PrintWriter}
import scala.util.{Random, Try}

/**
 * Utility object used for disk operation management.
 */
object DiskManager:

  import java.io.{File, FileInputStream, FileOutputStream, ObjectOutputStream, PrintWriter}
  import scala.io.Source

  /**
   * Method responsible of the implicit conversion from string to file.
   *
   * @param fileName : name of the file
   * @return : an istance of File named after the input string
   */
  implicit def stringToFile(fileName:String): File= new File(fileName)
  
  /**
   * Method that checks if a file does exists.
   *
   * @param fileName : name of the file to check
   * @return : true if the file already exists, or false if it does not
   */
  def isExisting(fileName: String): Boolean =
    fileName.exists()

  /**
   * Method that saves an object in a file, or prints a log string in case of failure.
   *
   * @param obj : instance of the object to be saved in the file
   * @param fileName : name of the file where the object should be saved
   * @tparam A : type of the object to be saved
   */
  def writeObject[A](obj: A, fileName: String): Unit =
    val objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))
    Try {
      objectOutputStream.writeObject(obj)
      }.toEither match {
        case Left(error) =>
          println("Error in saving the file")
        case Right(_) =>
          objectOutputStream.close
      }

  /**
   * Method that loads an object from an existing file, or prints a log string in case of failure.
   *
   * @param fileName : name of the file that should contain the object
   * @tparam A : type of the object
   * @return : None in case of failure, or an optional of the object loaded
   */
  def readObject[A](fileName: String): Option[A] =
    val objectInputStream = new ObjectInputStream(new FileInputStream(fileName))
    var res: Option[A]= None
    Try {
      res=Some(objectInputStream.readObject.asInstanceOf[A])
      }.toEither match {
        case Left(error) =>
          println("Error in reading the file")
          None
        case Right(_) =>
          objectInputStream.close
          res
    }

  /**
   * Method that deletes an existing file; it logs an error in case of failure, or a positive message in case of success.
   *
   * @param fileName : name of the file
   */
  def deleteFile(fileName: String): Unit =
    Try {
      fileName.delete()
    }.toEither match{
      case Left(error) =>
        println("Error deleting the file")
      case Right(_) =>
        println("File successfully deleted")
    }

