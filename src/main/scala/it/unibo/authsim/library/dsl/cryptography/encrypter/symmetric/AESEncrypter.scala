package it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.util.Base64
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.AES
import it.unibo.authsim.library.dsl.cryptography.encrypter.BasicEncrypter

import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, SecretKeySpec}
import java.util.*

object AESEncrypter:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._
  def apply() = new AESEncrypterImpl()

  case class AESEncrypterImpl() extends BasicEncrypter:
    var algorithm : AES = AES()
  
    private val _trasformation: String = "AES/ECB/PKCS5PADDING"

    /**
     * 
     * @param mode                    Mode in with the method must operate, either as a decrypter or an encrypter
     * @param password                Password to be encrypted or decrypted
     * @param secret                  Secred to encrypt or decrypt the password
     * @tparam A                      Generic parameter for the password
     * @tparam B                      Generic parameter for the secret
     *  @return                        A string representing the password either encrypted or decrypted
     */
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
      var keyBytes: Array[Byte] = secret.concat(algorithm.salt.get)
      keyBytes= Arrays.copyOf(keyBytes, algorithm.keyLength)
      new SecretKeySpec(keyBytes, algorithm.algorithmName)