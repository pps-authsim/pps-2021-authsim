package it.unibo.authsim.library.dsl.cryptography.symmetric

import  it.unibo.authsim.library.dsl.cryptography.BasicEcryption
import it.unibo.authsim.library.dsl.cryptography.{CryptographicAlgorithm}
import it.unibo.authsim.library.dsl.cryptography.util.Base64

import java.io.*
import java.security.spec.*

import javax.crypto.*
import javax.crypto.spec.*

trait DES extends SymmetricEncryption:
  def secretSalt(): String
  def iterationCount_ (key:Int): Unit

object DES:
  def apply()= new BasicDES()
    class BasicDES() extends BasicEcryption with DES:
      import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._
      //Array.fill[Byte](8)(0)
      private val _salt: Array[Byte] = Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte], 0xC8.asInstanceOf[Byte], 0x32.asInstanceOf[Byte], 0x56.asInstanceOf[Byte], 0x35.asInstanceOf[Byte], 0xE3.asInstanceOf[Byte], 0x03.asInstanceOf[Byte])

      private var _iterationCount: Int = 19

      override val _name: String = "DES"

      private val _trasformation : String = "PBEWithMD5AndDES"

      def secretSalt() = _salt

      override def iterationCount_(key: Int): Unit =
        _iterationCount = key

      override def crypto[A, B](mode:EncryptionMode, password: A, secret: B): String=
        var cipher = Cipher.getInstance(_trasformation)
        var _secretKey: SecretKey = secretKey(secret)
        var paramSpec: AlgorithmParameterSpec = new PBEParameterSpec(_salt, _iterationCount)
        mode match{
          case EncryptionMode.Encryption =>
            cipher.init(Cipher.ENCRYPT_MODE, _secretKey, paramSpec)
            new String(Base64.encodeToArray(cipher.doFinal(password)))
          case EncryptionMode.Decryption =>
            cipher.init(Cipher.DECRYPT_MODE, _secretKey, paramSpec)
            new String(cipher.doFinal(Base64.decodeToArray(password)))
        }

      private def secretKey(secret: String): SecretKey =
        var keySpec: KeySpec = new PBEKeySpec(secret, _salt, _iterationCount)
        SecretKeyFactory.getInstance(_trasformation).generateSecret(keySpec)