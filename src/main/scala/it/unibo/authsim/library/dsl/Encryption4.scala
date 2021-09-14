package it.unibo.authsim.library.dsl

import org.apache.commons.codec.binary.Hex

import java.security.Security
import javax.crypto.spec.SecretKeySpec
import javax.crypto.{Cipher, Mac}
import javax.crypto.spec.IvParameterSpec

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
