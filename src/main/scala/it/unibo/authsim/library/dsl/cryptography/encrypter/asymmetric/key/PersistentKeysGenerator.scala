package it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key

import it.unibo.authsim.library.dsl.cryptography.algorithm.AsymmetricEncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.util.{Base64, DiskManager as Disk}
import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import java.io.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.{KeyPairGenerator, KeyPair as JavaKeyPair}
import scala.io.Source

/**
 * Trait for the generation of a generic Key
 * @tparam A          Type of the key to be created
 */
trait KeyGenerator[A]:
  /**
   * Setter for the algorithm to be used for the generation of the key pair
   * 
   * @param algorithm     algorithm to be used for the generation of the key pair
   */
  def algorithm_(algorithm: AsymmetricEncryptionAlgorithm): Unit

  /**
   * Getter for the algorithm to be used for the generation of the key pair
   * @return              an istance of the algorithm to be used for the generation of the key pair
   */
  def algorithm: AsymmetricEncryptionAlgorithm

  /**
   * Method used to generate the key pair and saving it on the disk
   * 
   * @param fileName      name of the file where the key pair should be saved
   * @return              the key pair
   */
  def generateKeys(fileName: String): A

  /**
   * Method used to load an existing key pair from disk
   * 
   * @param fileName      name of the file where the key pair should have been saved
   * @return              the key pair
   */
  def loadKeys(fileName: String):A

//Singleton

/**
 * Object for the key generation
 */
object KeysGenerator extends KeyGenerator[KeyPair]:
  /**
   * Variable representing the instance of the agorithm used for the generation of the key pair
   */
  private var _algorithm: AsymmetricEncryptionAlgorithm = RSA()

  /**
   * Setter for the algorithm to be used for the generation of the key pair
   * 
   * @param algorithm     algorithm to be used for the generation of the key pair
   */
  override def algorithm_(algorithm: AsymmetricEncryptionAlgorithm): Unit= _algorithm=algorithm

  /**
   * Getter for the algorithm to be used for the generation of the key pair
   * 
   *  @return              an istance of the algorithm to be used for the generation of the key pair
   */
  override def algorithm: AsymmetricEncryptionAlgorithm= _algorithm

  /**
   * Method used to generate the key pair and saving it on the disk
   * 
   * @param fileName      name of the file where the key pair should be saved
   *  @return             the key pair
   */
  override def generateKeys(fileName: String): KeyPair =
    val keyGen = KeyPairGenerator.getInstance(_algorithm.algorithmName)
    keyGen.initialize(_algorithm.keyLength)
    val keyPair = keyGen.generateKeyPair
    saveKeys(keyPair, fileName)
    keyPair

  /**
   * Method used to load an existing key pair from disk, in case the file does not exists, or cause an exception during the reading operation
   * it creates a new file and save a new key pair on it
   * 
   * @param fileName      name of the file where the key pair should have been saved
   *  @return             the key pair on the file, or a new key pair
   */
  override def loadKeys(fileName: String): KeyPair =
    if (Disk.isExisting(fileName))then
      val optKey=Disk.readObject(fileName)
      optKey.getOrElse(generateKeys(fileName))
    else
      generateKeys(fileName)

  /**
   * Private method to actually call the utility responsible of writing the new key pair on the file
   * @param keypair         a key pair
   * @param fileName        a file name where the key pair should be saved
   */
  private def saveKeys(keypair:KeyPair, fileName: String) : Unit= Disk.writeObject(keypair, fileName)

  /**
   * Implicit class responsible of the conversion of the JavaKeyPair in the trait KeyPair
   * @param keypair
   */
  implicit class KeyPairImpl(keypair:JavaKeyPair) extends Serializable with KeyPair:
    /**
     * Method used to encode the key from a byte array to a string 
     * 
     * @param key           key to be enconded
     * @return              the byte array key encoded as string 
     */
    implicit def encode(key:  Array[Byte]): String = Base64.encodeToString(key)

    /**
     * Getter for the public key 
     * 
     *  @return          the public key encoded as string
     */
    def publicKey: String= keypair.getPublic.getEncoded

    /**
     * Getter for the private key 
     * 
     *  @return          the private key encoded as string
     */
    def privateKey: String= keypair.getPrivate.getEncoded