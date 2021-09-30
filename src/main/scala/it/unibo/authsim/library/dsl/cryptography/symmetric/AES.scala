package it.unibo.authsim.library.dsl.cryptography.symmetric

import it.unibo.authsim.library.dsl.cryptography.symmetric.SymmetricEncryption
import it.unibo.authsim.library.dsl.cryptography.{BasicEcryption, CryptographicAlgorithm}
import it.unibo.authsim.library.dsl.cryptography.util.Base64
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction

import java.security.MessageDigest
import java.security.spec.KeySpec
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, SecretKeySpec}
import java.util.*

trait AES extends SymmetricEncryption:
  def secretSalt(): String

object AES:
  def apply()= new BasicAES()
    class BasicAES() extends BasicEcryption with AES:
    import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

    private val _length : Int = 16
    override val _name : String ="AES"
    private var _salt: String = "123456789"
    private val _trasformation: String = "AES/ECB/PKCS5PADDING"

    override def secretSalt(): String= _salt

    override def crypto[A,B](mode:EncryptionMode, password: A, secret: B): String=
      val cipher: Cipher = Cipher.getInstance(_trasformation)
      mode match{
        case EncryptionMode.Encryption =>
          cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec(secret))
          new String(Base64.encodeToArray(cipher.doFinal(password)))
        case EncryptionMode.Decryption =>
          cipher.init(Cipher.DECRYPT_MODE, secretKeySpec(secret))
          new String(cipher.doFinal(Base64.decodeToArray(password)))
      }

    private def secretKeySpec(secret: String): SecretKeySpec =
      var keyBytes: Array[Byte] = secret.concat(_salt)
      keyBytes = Arrays.copyOf(keyBytes, _length)
      new SecretKeySpec(keyBytes, _name)