package it.unibo.authsim.library.dsl.cryptography.util

/**
 * Utility object used for encoding and decoding operation
 */
object Base64:
  import java.util.Base64.{getDecoder, getEncoder}

  /**
   * Method to decode an array of byte as an array of bytes
   * 
   * @param bytes         byte array to decoded
   * @return              byte array decoded 
   */
  def decodeToArray(bytes: Array[Byte]): Array[Byte] = getDecoder.decode(bytes)
  /**
   * Method to decode an array of byte as a string
   *
   * @param bytes         byte array to decoded
   * @return              string representing the result of the decoded operation applied to the byte array
   */
  def decodeToString(bytes: Array[Byte]): String = new String(decodeToArray(bytes))

  /**
   * Method to encode an array of byte as an array of bytes
   *
   * @param bytes         byte array to encoded
   * @return              byte array encoded 
   */
  def encodeToArray(bytes: Array[Byte]): Array[Byte] = getEncoder.encode(bytes)
  /**
   * Method to encode an array of byte as a string
   *
   * @param bytes         byte array to encoded
   * @return              string representing the result of the encoded operation applied to the byte array
   */
  def encodeToString(bytes: Array[Byte]): String = getEncoder.encodeToString(bytes)

