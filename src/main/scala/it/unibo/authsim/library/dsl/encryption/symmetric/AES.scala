package it.unibo.authsim.library.dsl.encryption.symmetric

import it.unibo.authsim.library.dsl.encryption.symmetric.util.Util.{EncryptionMode, toMultiple}
import it.unibo.authsim.library.dsl.encryption.SymmetricEncryption

import org.apache.commons.codec.binary.Hex

import java.security.Security
import java.util.Base64
import javax.crypto.*
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

trait AES extends SymmetricEncryption:
  override def encrypt(password: String, secret:String): String
  override def decrypt(password: String, secret:String): String
  def iv_ (iv:String): Unit

object AES:
  implicit def stringToByteArray(value : String):Array[Byte] = Hex.decodeHex(value.toCharArray)

  def apply()= new AES() :
    var _iv:String="12345678123456781234567812345678"
    var _paramSpec = new IvParameterSpec(_iv)

    override def iv_(iv: String): Unit =
      _iv=iv

    override def encrypt(password: String, key: String): String=
      crypto(EncryptionMode.Encryption, password, key)

    override def decrypt(password: String, key: String): String=
      crypto(EncryptionMode.Decryption, password, key)

    private def crypto(mode:EncryptionMode, password: String, key: String): String=
      val input = password.getBytes("UTF-8")
      val secretKeySpec = new SecretKeySpec(key, "AES")
      val cipher = Cipher.getInstance("AES/CTR/NoPadding")
      mode match{
        case EncryptionMode.Encryption => cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, _paramSpec)
        case EncryptionMode.Decryption => cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, _paramSpec)
      }
      cipher.doFinal(input)
      new String(input, "UTF8")

object App:
  def main(args: Array[String]): Unit =
    //key 4 byte
    val password: String="my super secret input!!!"
    val key:String="12345678123456781234567812345678"
    val s=AES()
    val encrypted2 = s.encrypt(password, key)
    val decrypted2 = s.decrypt(encrypted2, key)
    println("Input:" + password)
    println("Input decrypted: " + decrypted2)

    val prova=toMultiple("a")
    println(prova)
