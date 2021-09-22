package it.unibo.authsim.library.dsl.encryption.util

import java.io.ObjectInputStream

object Util:
  def toMultiple(key: String): String = key match {
    case key if (key.length < 16) => key.concat("0")
    case _ => key
  }
/*
object PersistentKeys:
  def loadOrCreate(fileName: String = "key.ser"): PersistentKeys =
    val keyPair =
      if (Disk.isExisting(fileName)) then
        Disk.loadObject[KeyPair](fileName)
      else
        val kp = generateNewKeyPair
        Disk.saveObject(kp, fileName)
        kp

    new PersistentKeys(fileName, keyPair)

  class PersistentKeys private ( val fileName: String,
                                 private var myKeyPair: KeyPair) extends Keys:
    def updateKey(): Unit =
      myKeyPair = generateNewKeyPair
      Disk.saveObject(myKeyPair, fileName)


  override def publicKey: String = publicKeyStringFromKeyPair(myKeyPair)
  override def privateKey: String = privateKeyStringFromKeyPair(myKeyPair)
*/