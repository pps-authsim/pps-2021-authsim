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
    try oos.writeObject(obj) finally oos.close

  def loadObject[T](fileName: String): T =
    val file = new File(fileName)
    val objectInputStream = new ObjectInputStream(new FileInputStream(file))
    try {
      objectInputStream.readObject.asInstanceOf[T]
    } finally objectInputStream.close
