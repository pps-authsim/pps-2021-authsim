package it.unibo.authsim.library.dsl

import org.apache.commons.codec.binary.Hex

import java.security.Security
import javax.crypto.spec.SecretKeySpec
import javax.crypto.{Cipher, Mac}
import javax.crypto.spec.IvParameterSpec

trait Encryption

object AESCTR:
  //DES,
  def encrypt(input: Array[Byte], key: Array[Byte], iv: Array[Byte]): Array[Byte] =
    val keySpec = new SecretKeySpec(key, "AES")
    val ivSpec = new IvParameterSpec(iv)
    val cipher = Cipher.getInstance("AES/CTR/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
    cipher.doFinal(input)


  def decrypt(input: Array[Byte], key: Array[Byte], iv: Array[Byte]): Array[Byte] =
    val keySpec = new SecretKeySpec(key, "AES")
    val ivSpec = new IvParameterSpec(iv)
    val cipher = Cipher.getInstance("AES/CTR/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
    cipher.doFinal(input)

  def encrypt2(password: String, key: String, iv:String): String=
    val input = password.getBytes("UTF-8")
    val key2 = Hex.decodeHex(key.toCharArray)
    val iv2 = Hex.decodeHex(iv.toCharArray)
    val keySpec = new SecretKeySpec(key2, "AES")
    val ivSpec = new IvParameterSpec(iv2)
    val cipher = Cipher.getInstance("AES/CTR/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
    cipher.doFinal(input)
    new String(input, "UTF-8")

  def decrypt2(passwordEcrypted: String, key: String, iv:String): String=
    val input = passwordEcrypted.getBytes("UTF-8")
    val key2 = Hex.decodeHex(key.toCharArray)
    val iv2 = Hex.decodeHex(iv.toCharArray)
    val keySpec = new SecretKeySpec(key2, "AES")
    val ivSpec = new IvParameterSpec(iv2)
    val cipher = Cipher.getInstance("AES/CTR/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
    cipher.doFinal(input)
    new String(input, "UTF-8")



object App:
  def main(args: Array[String]): Unit =
    val input = "my super secret input!!!".getBytes("UTF-8")
    // For key consider using a "Password Based Key Generation", like PBKDF2, SCRIPT, ...
    val key = Hex.decodeHex("000102030405060708090a0b0c0d0e0f".toCharArray)
    val iv = Hex.decodeHex("01020304050607080910111201010101".toCharArray)
    val encrypted = AESCTR.encrypt(input, key, iv)
    val decrypted = AESCTR.decrypt(encrypted, key, iv)

    println("input: " + new String(input, "UTF-8"))
    println("decrypted: " + new String(decrypted, "UTF-8"))

    val encrypted2 = AESCTR.encrypt2("my super secret input!!!", "000102030405060708090a0b0c0d0e0f", "01020304050607080910111201010101")
    val decrypted2 = AESCTR.decrypt2(encrypted2, "000102030405060708090a0b0c0d0e0f", "01020304050607080910111201010101")

    println("decrypted2: " + decrypted2)
