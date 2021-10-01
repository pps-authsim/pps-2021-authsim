package it.unibo.authsim.library.dsl.cryptography.util

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream, PrintWriter}
import scala.util.{Random, Try}

object DiskManager:

  import java.io.{File, FileInputStream, FileOutputStream, ObjectOutputStream, PrintWriter}
  import scala.io.Source

  def isExisting(fileName: String): Boolean =
    new File(fileName).exists()

  def saveObject[T](obj: T, fileName: String): Unit =
    val file = new File(fileName)
    val oos = new ObjectOutputStream(new FileOutputStream(file))

    Try {
      oos.writeObject(obj)
      }.toEither match {
        case Left(error) =>
          println("Error in saving the file")
        case Right(_) =>
          oos.close
      }

  def loadObject[T](fileName: String): Option[T] =
    val file = new File(fileName)
    val objectInputStream = new ObjectInputStream(new FileInputStream(file))
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
