package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.symmetric.SymmetricEncryption
import it.unibo.authsim.library.dsl.cryptography.{CryptographicAlgorithm, EncryptionMode}
import it.unibo.authsim.library.dsl.cryptography.util.Base64 as Base64
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction

import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, SecretKeySpec}


trait AES extends SymmetricEncryption:
  def secretSalt(): String

object AES:
  def apply()= new AES() :
    private val _length : Int = 16 
    private val _name : String ="AES"
    private var _salt: String = "123456789"
    private val _charset: String = "UTF8"
    private val _trasformation: String = "AES/ECB/PKCS5PADDING"

    override def algorithmName: String = _name
    override def secretSalt(): String= _salt

    def encrypt(password: String, secret: String): String =
      crypto(EncryptionMode.Encryption, password, secret)

    def decrypt(encryptedPassword: String, secret:String): String =
      crypto(EncryptionMode.Decryption, encryptedPassword, secret)

    private def crypto(mode:EncryptionMode, password: String, secret: String): String=
      val cipher: Cipher = Cipher.getInstance(_trasformation)
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, keyToSpec(secret))
          new String(Base64.encodeToBytes(cipher.doFinal(password.getBytes(_charset))))
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, keyToSpec(secret))
          new String(cipher.doFinal(Base64.decodeToBytes(password)))
      }

    implicit def ArrayByteToString(value :Array[Byte]):String =value.toString

    override def toString: String = "AES"

    private def keyToSpec(secret: String): SecretKeySpec =
      var hashFunctionSHA256 = new HashFunction.SHA256
      var keyBytes: Array[Byte] = hashFunctionSHA256.hash(_salt + secret).getBytes(_charset)
      keyBytes = util.Arrays.copyOf(keyBytes, _length)
      new SecretKeySpec(keyBytes, _name)