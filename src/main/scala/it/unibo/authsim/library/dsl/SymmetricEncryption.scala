package it.unibo.authsim.library.dsl

import org.apache.commons.codec.binary.Hex

import java.security.Security
import java.util.Base64
import javax.crypto.spec.SecretKeySpec
import javax.crypto.{Cipher, KeyGenerator, Mac, SecretKey, SecretKeyFactory}
import javax.crypto.spec.IvParameterSpec

trait Encryption

object Util:
  def toMultiple(key: String): String= key match{
    case key if(key.length<16) => key.concat("0")
    case _=> key
  }


object SymmetricEncryption:
  //DES,
  //private def generateKey(algorithm: String):String=
    //val keyGen: KeyGenerator = KeyGenerator.getInstance(algorithm).init(64)
    //Base64.encode(keyGen.generateKey())
  implicit def stringToByteArray(value : String):Array[Byte] = Hex.decodeHex(value.toCharArray)

  def encrypt(password: String, key: String, iv:String, algorithm: String): String=
    val input = password.getBytes("UTF-8")
    val keySpec = new SecretKeySpec(key, algorithm)
    val ivSpec = new IvParameterSpec(iv)
    val cipher = Cipher.getInstance(algorithm+"/CTR/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
    cipher.doFinal(input)
    new String(input, "UTF-8")

  def decrypt(passwordEcrypted: String, key: String, iv:String, algorithm: String): String=
    val input = passwordEcrypted.getBytes("UTF-8")
    val keySpec = new SecretKeySpec(key, algorithm)
    val ivSpec = new IvParameterSpec(iv)
    val cipher = Cipher.getInstance(algorithm+"/CTR/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
    cipher.doFinal(input)
    new String(input, "UTF-8")

object App:
  def main(args: Array[String]): Unit =

//key 4 byte
    val password: String="my super secret input!!!"
    val key:String="12345678123456781234567812345678"
    val iv:String="12345678123456781234567812345678"
    val algorithm:String="AES"
    val encrypted2 = SymmetricEncryption.encrypt(password, key, iv, algorithm)
    val decrypted2 = SymmetricEncryption.decrypt(encrypted2, key, iv, algorithm)
    println("Input:" + password)
    println("Input decrypted: " + decrypted2)

    val prova=Util.toMultiple("a")
    println(prova)