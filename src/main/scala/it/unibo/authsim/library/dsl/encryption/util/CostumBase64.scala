package it.unibo.authsim.library.dsl.encryption.util

object CostumBase64:
  import java.util.Base64.{getDecoder, getEncoder}
  def decodeToBytes(s: String): Array[Byte] = getDecoder.decode(s)
  def decodeToBytes(bytes: Array[Byte]): Array[Byte] = getDecoder.decode(bytes)
  def decodeToString(s: String): String = new String(decodeToBytes(s))
  def decodeToString(bytes: Array[Byte]): String = new String(decodeToBytes(bytes))

  def encodeToBytes(bytes: Array[Byte]): Array[Byte] = getEncoder.encode(bytes)
  def encodeToBytes(s: String): Array[Byte] = getEncoder.encode(s.getBytes)
  def encodeToString(bytes: Array[Byte]): String = getEncoder.encodeToString(bytes)
  def encodeToString(s: String): String = encodeToString(s.getBytes)
