package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.{CryptographicAlgorithm, EncryptionMode}

import it.unibo.authsim.library.dsl.cryptography.util.Base64 as Base64

import java.io.*
import java.security.spec.*

import javax.crypto.*
import javax.crypto.spec.*

trait DES extends SymmetricEncryption:
  def secretSalt(): String
  def iterationCount_ (key:Int): Unit

object DES:
  def apply()= new DES() :
    import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._
    private var _salt: Array[Byte] = Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte], 0xC8.asInstanceOf[Byte], 0x32.asInstanceOf[Byte], 0x56.asInstanceOf[Byte], 0x35.asInstanceOf[Byte], 0xE3.asInstanceOf[Byte], 0x03.asInstanceOf[Byte])
    private var _iterationCount: Int = 19
    private val _name: String = "DES"
    private val _algorithm : String = "PBEWithMD5AndDES"
    private val charset: String = "UTF8"

    def secretSalt()=_salt

    override def algorithmName: String = _name

    override def encrypt[A,B](password: A, secret: B): String =
      crypto(EncryptionMode.Encryption, password, secret)

    override def decrypt[A, B](encryptedPassword: A, secret:B): String =
      crypto(EncryptionMode.Decryption, encryptedPassword, secret)

    private def crypto(mode:EncryptionMode, password: String, secret: String): String=
      var secretKeySpec: SecretKey = keyToSpec(secret)
      var cipher = Cipher.getInstance(secretKeySpec.getAlgorithm)
      var _paramSpec: AlgorithmParameterSpec = new PBEParameterSpec(_salt, _iterationCount)
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, _paramSpec)
          new String(Base64.encodeToBytes(cipher.doFinal(password)), charset)
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, _paramSpec)
          new String(cipher.doFinal(Base64.decodeToBytes(password)), charset)
      }
    override def toString: String = "DES"
    
    override def iterationCount_(key: Int): Unit = _iterationCount = key

    private def keyToSpec(secret: String): SecretKey =
      var keySpec: KeySpec = new PBEKeySpec(secret, _salt, _iterationCount)
      SecretKeyFactory.getInstance(_algorithm).generateSecret(keySpec)