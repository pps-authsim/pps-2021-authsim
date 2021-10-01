package it.unibo.authsim.library.dsl.cryptography.encrypter.symmetric

import it.unibo.authsim.library.dsl.cryptography.algorithm.SymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.DES
import it.unibo.authsim.library.dsl.cryptography.encrypter.BasicEcrypter
import it.unibo.authsim.library.dsl.cryptography.util.Base64

import java.io.*
import java.security.spec.*
import java.util.Arrays
import javax.crypto.*
import javax.crypto.spec.*

object DESEncrypter extends BasicEcrypter:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  var algorithm : DES = DES()

  val salt = Arrays.copyOf(algorithm.salt, 8)

  private var _iterationCount: Int = 19

  def iterationCount_(iteration: Int): Unit =
    _iterationCount = iteration

  private val _trasformation : String = "PBEWithMD5AndDES"

  override def crypto[A, B](mode:EncryptionMode, password: A, secret: B): String=
    var cipher = Cipher.getInstance(_trasformation)
    var _secretKey: SecretKey = secretKey(secret)
    var paramSpec: AlgorithmParameterSpec = new PBEParameterSpec(salt, _iterationCount)
    mode match{
      case EncryptionMode.Encryption =>
        cipher.init(Cipher.ENCRYPT_MODE, _secretKey, paramSpec)
        new String(Base64.encodeToArray(cipher.doFinal(password)))
      case EncryptionMode.Decryption =>
        cipher.init(Cipher.DECRYPT_MODE, _secretKey, paramSpec)
        new String(cipher.doFinal(Base64.decodeToArray(password)))
    }

  private def secretKey(secret: String): SecretKey =
    //var newsecret=Arrays.copyOf(secret, algorithm.keyLength)
    //println(newsecret.length)
    var keySpec: KeySpec = new PBEKeySpec(secret, salt ,_iterationCount)
    SecretKeyFactory.getInstance(_trasformation).generateSecret(keySpec)