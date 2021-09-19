package it.unibo.authsim.library.dsl.encryption.symmetric

import it.unibo.authsim.library.dsl.encryption.symmetric.util.Util.EncryptionMode
import it.unibo.authsim.library.dsl.encryption.SymmetricEncryption

import java.io.*
import java.security.spec.*
import java.util.Base64
import javax.crypto.*
import javax.crypto.spec.*

trait DES extends SymmetricEncryption:
  override def encrypt(password: String, secret:String): String
  override def decrypt(password: String, secret:String): String
  def secretSalt_(key:Array[Byte]): Unit
  def iterationCount_ (key:Int): Unit

object DES:
  def apply()= new DES() :
    private var _salt: Array[Byte] = Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte], 0xC8.asInstanceOf[Byte], 0x32.asInstanceOf[Byte], 0x56.asInstanceOf[Byte], 0x35.asInstanceOf[Byte], 0xE3.asInstanceOf[Byte], 0x03.asInstanceOf[Byte])
    private var _iterationCount: Int = 19
    private var _paramSpec: AlgorithmParameterSpec = new PBEParameterSpec(_salt, _iterationCount)

    implicit def stringToCharArray(value : String):Array[Char] =value.toCharArray
    implicit  def stringToArrayByte(value : String):Array[Byte] =value.getBytes("UTF8")

    override def encrypt(password: String, secret: String): String =
      crypto(EncryptionMode.Encryption, password, secret)

    override def decrypt(encryptedPassword: String, secret:String): String =
      crypto(EncryptionMode.Decryption, encryptedPassword, secret)

    private def crypto(mode:EncryptionMode, password: String, secret: String): String=
      var keySpec: KeySpec = new PBEKeySpec(secret, _salt, _iterationCount)
      var secretKeySpec: SecretKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec)
      var cipher = Cipher.getInstance(secretKeySpec.getAlgorithm)
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, _paramSpec)
          new String(Base64.getEncoder.encode(cipher.doFinal(password)), "UTF8")
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, _paramSpec)
          new String(cipher.doFinal(Base64.getDecoder.decode(password)), "UTF8")
      }

    override def iterationCount_(key: Int): Unit =
      _iterationCount = key

    override def secretSalt_(salt: Array[Byte]): Unit =
      _salt = salt
    
object App2:
  def main(args: Array[String]): Unit =
    val secret: String = "12345678123456781234567812345678"
    val password: String = "password"
    val des = DES()
    val enc=des.encrypt(password, secret)
    val dec=des.decrypt(enc, secret)
    println("password: "+ password)
    println("value encrypted: "+ enc)
    println("value decrypted: "+ dec)