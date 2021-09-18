package it.unibo.authsim.library.dsl.encryption.symmetric.util

object Util:
  enum EncryptionMode:
    case Decryption, Encryption

  def toMultiple(key: String): String = key match {
    case key if (key.length < 16) => key.concat("0")
    case _ => key
  }
