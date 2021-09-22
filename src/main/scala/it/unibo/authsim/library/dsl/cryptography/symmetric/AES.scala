package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.{CryptographicAlgorithm, EncryptionMode, SymmetricEncryption}
import it.unibo.authsim.library.dsl.cryptography.util.Util.toMultiple
import it.unibo.authsim.library.dsl.cryptography.util.CostumBase64 as Base64
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction

import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, SecretKeySpec}


trait AES extends SymmetricEncryption:
  override def algorithmName: String
  override def encrypt(password: String, secret:String): String
  override def decrypt(password: String, secret:String): String
  def secretSalt(): String

object AES:
  def apply()= new AES() :
    private val _name="AES"
    private var _salt: String = "123456789"

    override def algorithmName: String = _name
    override def secretSalt(): String= _salt

    def encrypt(password: String, secret: String): String =
      crypto(EncryptionMode.Encryption, password, secret)

    def decrypt(encryptedPassword: String, secret:String): String =
      crypto(EncryptionMode.Decryption, encryptedPassword, secret)

    private def crypto(mode:EncryptionMode, password: String, secret: String): String=
      val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, keyToSpec(secret))
          new String(Base64.encodeToBytes(cipher.doFinal(password.getBytes("UTF8"))))
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, keyToSpec(secret))
          new String(cipher.doFinal(Base64.decodeToBytes(password)))
      }

    implicit def ArrayByteToString(value :Array[Byte]):String =value.toString

    private def keyToSpec(secret: String): SecretKeySpec =
      var hashFunctionSHA256 = new HashFunction.SHA256
      var keyBytes: Array[Byte] = hashFunctionSHA256.hash(_salt + secret).getBytes("UTF8")
      keyBytes = util.Arrays.copyOf(keyBytes, 16)
      new SecretKeySpec(keyBytes, _name)

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