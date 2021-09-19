package it.unibo.authsim.library.dsl.encryption.symmetric

import it.unibo.authsim.library.dsl.encryption.SymmetricEncryption
import it.unibo.authsim.library.dsl.encryption.symmetric.util.Util.{EncryptionMode, toMultiple}
import it.unibo.authsim.library.dsl.encryption.hash.HashFunction

import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, SecretKeySpec}
import java.util.Base64

trait AES extends SymmetricEncryption:
  override def encrypt(password: String, secret:String): String
  override def decrypt(password: String, secret:String): String
  def secretSalt_(key:String): Unit

object AES:
  def apply()= new AES() :
    def encrypt(password: String, secret: String): String =
      crypto(EncryptionMode.Encryption, password, secret)

    def decrypt(encryptedPassword: String, secret:String): String =
      crypto(EncryptionMode.Decryption, encryptedPassword, secret)

    private def crypto(mode:EncryptionMode, password: String, secret: String): String=
      val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, keyToSpec(secret))
          new String(Base64.getEncoder.encode(cipher.doFinal(password.getBytes("UTF8"))))
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, keyToSpec(secret))
          new String(cipher.doFinal(Base64.getDecoder.decode(password)))
      }

    implicit def stringToArrayByte(value :Array[Byte]):String =value.toString

    def keyToSpec(secret: String): SecretKeySpec =
      var keyBytes: Array[Byte] = (_salt + secret).getBytes("UTF8")
      var hashFunctionSHA256 = new HashFunction.SHA256
      hashFunctionSHA256.hash(keyBytes)
      keyBytes = util.Arrays.copyOf(keyBytes, 16)
      new SecretKeySpec(keyBytes, "AES")

    override def secretSalt_(salt: String): Unit =
        _salt = salt

    private var _salt: String =
      "jMhKlOuJnM34G6NHkqo9V010GhLAqOpF0BePojHgh1HgNg8^72k"


object App3:
  def main(args: Array[String]): Unit =
    val secret = "12345678123456781234567812345678"
    val password="password"
    val aes= AES()
    val enc=aes.encrypt(password, secret)
    val dec=aes.decrypt(enc, secret)
    println("password: "+ password)
    println("value encrypted: "+ enc)
    println("value decrypted: "+ dec)