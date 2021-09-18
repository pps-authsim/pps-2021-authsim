import it.unibo.authsim.library.dsl.SymmetricEncryption
import it.unibo.authsim.library.dsl.util.Util.EncryptionMode
import java.util.Base64
import javax.crypto.*
import javax.crypto.spec.*
import java.security.spec.*
import java.io.*

trait DES extends SymmetricEncryption:
  override def encrypt(password: String, secret:String): String
  override def decrypt(password: String, secret:String): String
  def secreSalt_ (key:Array[Byte]): Unit
  def iterationCount_ (key:Int): Unit

object DES :
  import it.unibo.authsim.library.dsl.util.Util
  def apply()= new DES() :
    var _salt: Array[Byte] = Array(0xA9.asInstanceOf[Byte], 0x9B.asInstanceOf[Byte], 0xC8.asInstanceOf[Byte], 0x32.asInstanceOf[Byte], 0x56.asInstanceOf[Byte], 0x35.asInstanceOf[Byte], 0xE3.asInstanceOf[Byte], 0x03.asInstanceOf[Byte])
    var _iterationCount: Int = 19
    var _paramSpec: AlgorithmParameterSpec = new PBEParameterSpec(_salt, _iterationCount)

    implicit def stringToCharArray(value : String):Array[Char] =value.toCharArray
    implicit  def stringArrayByte(value : String):Array[Byte] =value.getBytes("UTF8")

    override def encrypt(password: String, secret: String): String =
      crypto(EncryptionMode.Encryption, password, secret)

    override def decrypt(password: String, secret:String): String =
      crypto(EncryptionMode.Decryption, password, secret)

    private def crypto(mode:EncryptionMode, password: String, secret: String): String=
      var keySpec: KeySpec = new PBEKeySpec(secret, _salt, _iterationCount)
      var key: SecretKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec)
      var ecipher = Cipher.getInstance(key.getAlgorithm)
      mode match{
        case EncryptionMode.Encryption =>
          ecipher.init(Cipher.ENCRYPT_MODE, key, _paramSpec)
          new String(Base64.getEncoder.encode(ecipher.doFinal(password)), "UTF8")
        case EncryptionMode.Decryption =>
          ecipher.init(Cipher.DECRYPT_MODE, key, _paramSpec)
          new String(ecipher.doFinal(Base64.getDecoder.decode(password)), "UTF8")
      }

    override def iterationCount_(key: Int): Unit =
      _iterationCount = key

    override def secreSalt_(key: Array[Byte]): Unit =
      _salt = key

object App:
  def main(args: Array[String]): Unit =
    val PASSPHRASE_API_KEY: String = "some pass phrase"
    val SESSION_SECRET_KEY: String = "another even longer phrase to be used"
    val c = DES()
    val enc=c.encrypt("prova", PASSPHRASE_API_KEY)
    val dec=c.decrypt(enc, PASSPHRASE_API_KEY)
    println(enc)
    println(dec)