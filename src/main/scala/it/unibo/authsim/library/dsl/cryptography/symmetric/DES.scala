package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.{CryptographicAlgorithm, EncryptionMode, SymmetricEncryption}
import it.unibo.authsim.library.dsl.cryptography.util.CostumBase64 as Base64

import java.io.*
import java.security.spec.*

import javax.crypto.*
import javax.crypto.spec.*

trait DES extends SymmetricEncryption:
  override def algorithmName: String
  override def encrypt(password: String, secret:String): String
  override def decrypt(password: String, secret:String): String
  def secretSalt(): String
  def iterationCount_ (key:Int): Unit

object DES:
  def apply()= new DES() :
    private var _salt: Array[Byte] = Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte], 0xC8.asInstanceOf[Byte], 0x32.asInstanceOf[Byte], 0x56.asInstanceOf[Byte], 0x35.asInstanceOf[Byte], 0xE3.asInstanceOf[Byte], 0x03.asInstanceOf[Byte])
    private var _iterationCount: Int = 19
    private val _name: String = "DES"

    implicit def stringToCharArray(value : String):Array[Char] =value.toCharArray
    implicit def stringToArrayByte(value : String):Array[Byte] =value.getBytes("UTF8")
    implicit def ArrayByteToString(value :Array[Byte]):String =value.toString

    def secretSalt()=_salt

    override def algorithmName: String = _name

    override def encrypt(password: String, secret: String): String =
      crypto(EncryptionMode.Encryption, password, secret)

    override def decrypt(encryptedPassword: String, secret:String): String =
      crypto(EncryptionMode.Decryption, encryptedPassword, secret)

    private def crypto(mode:EncryptionMode, password: String, secret: String): String=
      var secretKeySpec: SecretKey = keyToSpec(secret)
      var cipher = Cipher.getInstance(secretKeySpec.getAlgorithm)
      var _paramSpec: AlgorithmParameterSpec = new PBEParameterSpec(_salt, _iterationCount)
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, _paramSpec)
          new String(Base64.encodeToBytes(cipher.doFinal(password)), "UTF8")
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, _paramSpec)
          new String(cipher.doFinal(Base64.decodeToBytes(password)), "UTF8")
      }
    override def toString: String = "DES"
    
    override def iterationCount_(key: Int): Unit =
      _iterationCount = key

    private def keyToSpec(secret: String): SecretKey =
      var keySpec: KeySpec = new PBEKeySpec(secret, _salt, _iterationCount)
      SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec)

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

