import java.util.Base64
import javax.crypto._
import javax.crypto.spec._
import java.security.spec._
import java.io._

/**
 *
 * @author
 * Adapted from http://exampledepot.com/egs/javax.crypto/PassKey.html
 *
 */
object DesEncrypter {
  final val PASSPHRASE_API_KEY: String = "some pass phrase"
  final val SESSION_SECRET_KEY: String = "another even longer phrase to be used"
}

class DesEncrypter ( passPhrase: String ) {
  private var ecipher: Cipher = null
  private var dcipher: Cipher = null
  private var salt: Array[Byte] = Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte], 0xC8.asInstanceOf[Byte], 0x32.asInstanceOf[Byte], 0x56.asInstanceOf[Byte], 0x35.asInstanceOf[Byte], 0xE3.asInstanceOf[Byte], 0x03.asInstanceOf[Byte])
  private var iterationCount: Int = 19

  // Constructor

    var keySpec: KeySpec = new PBEKeySpec(passPhrase.toCharArray, salt, iterationCount)
    var key: SecretKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec)
    ecipher = Cipher.getInstance(key.getAlgorithm)
    dcipher = Cipher.getInstance(key.getAlgorithm)
    var paramSpec: AlgorithmParameterSpec = new PBEParameterSpec(salt, iterationCount)
    ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec)
    dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec)


  def encrypt(str: String): String = {
      var utf8: Array[Byte] = str.getBytes("UTF8")
      var enc: Array[Byte] = ecipher.doFinal(utf8)
      new String(Base64.getEncoder.encode(enc), "UTF8")

  }

  def decrypt(str: String): String = {

      var dec: Array[Byte] = Base64.getDecoder.decode(str)
      var utf8: Array[Byte] = dcipher.doFinal(dec)

      return new String(utf8, "UTF8")
  }

}

object App:
  def main(args: Array[String]): Unit =
    val c = new DesEncrypter(DesEncrypter.PASSPHRASE_API_KEY)
    val enc=c.encrypt("prova")
    val dec=c.decrypt(enc)
    println(enc)
    println(dec)