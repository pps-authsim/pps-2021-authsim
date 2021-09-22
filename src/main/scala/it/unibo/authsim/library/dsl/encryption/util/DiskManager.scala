package it.unibo.authsim.library.dsl.encryption.util

import java.io.ObjectInputStream

object DiskManager:

  import java.io.{File, FileInputStream, FileOutputStream, ObjectOutputStream, PrintWriter}
  import scala.io.Source

  def userDir: String = System.getProperty("user.home")

  def isExisting(fileName: String): Boolean =
    new File(fileName).exists()

  def createIfNotExist(fileName: String): Boolean =
    val file = new File(fileName)
    val exists = file.exists
    if (!exists) then
      file.getParentFile.mkdirs
      file.createNewFile
    !exists

  def saveObject[T](obj: T, fileName: String): Unit =
    val file = new File(fileName)
    val oos = new ObjectOutputStream(new FileOutputStream(file))
    try oos.writeObject(obj) finally oos.close

  def saveString(s: String, fileName: String, enc: String = "UTF-8"): Unit =
    val file = new File(fileName)
    val pw = new PrintWriter(file, enc)
    try pw.write(s) finally pw.close

  def saveLines(lines: Seq[String], fileName: String, enc: String = "UTF-8"): Unit =
    saveString(lines.mkString("\n"), fileName, enc)

  def loadObject[T](fileName: String): T =
    val file = new File(fileName)
    val objectInputStream = new ObjectInputStream(new FileInputStream(file))
    try {
      objectInputStream.readObject.asInstanceOf[T]
    } finally objectInputStream.close

  def loadString(fileName: String, enc: String = "UTF-8"): String =
    Source.fromFile(fileName, enc).getLines.mkString

  def loadLines(fileName: String, enc: String = "UTF-8"): Vector[String] =
    Source.fromFile(fileName, enc).getLines.toVector
