package it.unibo.authsim.library.dsl.cryptography.util

object Base64:
  import java.util.Base64.{getDecoder, getEncoder}
  def decodeToArray(bytes: Array[Byte]): Array[Byte] = getDecoder.decode(bytes)
  def decodeToString(bytes: Array[Byte]): String = new String(decodeToArray(bytes))

  def encodeToArray(bytes: Array[Byte]): Array[Byte] = getEncoder.encode(bytes)
  def encodeToString(bytes: Array[Byte]): String = getEncoder.encodeToString(bytes)

