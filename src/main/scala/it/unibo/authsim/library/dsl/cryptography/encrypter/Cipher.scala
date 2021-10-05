package it.unibo.authsim.library.dsl.cryptography.encrypter

import it.unibo.authsim.library.dsl.cryptography.algorithm.EncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.encrypter.asymmetric.key.KeyPair

import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

/**
 * Trait that represent a basic encryption
 */
trait Cipher:
  def algorithm : EncryptionAlgorithm

  /**
   * Enumeration that specify in wich mode is to be used either encrypt or decrypt
   */
  protected enum EncryptionMode:
    case Decryption, Encryption

  /**
   * Method used to encrypt a password using a generic secret
   *
   * @param password                password to be encrypted
   * @param secret                  secret used to encrypt the password
   * @tparam A                      generic parameter for the password
   * @tparam B                      generic parameter for the secret
   * @return                        a string representing the password encrypted
   */
  def encrypt[A, B](password: A, secret:B): String

  /**
   * Method used to decrypt a password using a generic secret
   *
   * @param encryptedPassword      encrypted password to be decrypted
   * @param secret                 secret used to encrypt the password
   * @tparam A                     generic parameter for the password
   * @tparam B                     generic parameter for the secret
   * @return                       a string representing the password decrypted
   */
  def decrypt[A, B](encryptedPassword: A, secret:B): String
  
/**
 * Trait for Symmetric encrypter
 */
trait SymmetricEncrypter extends Cipher

/**
 * Trait for Asymmetric encrypter, it provides additional methods to manage the encryption operation
 * using asymmetric encryption algorithms
 */
trait AsymmetricEncrypter extends Cipher:
  
  /**
   * Method to load existing key from a user directory 
   * 
   * @param fileName                name of the file from which key should be loaded
   * @return                        an istance of KeyPair
   */
  def loadKeys(fileName: String):KeyPair

  /**
   * Method to generate a new key pair and save it on the disk, overriding prexisting information present in the file
   * 
   * @param fileName                name of the file in which key pair should be saved
   * @return                        the key pair generated
   */
  def generateKeys(fileName: String): KeyPair

/**
 * Abstract class for to perform the encryption operation
 */
abstract class BasicCipher extends Cipher:
  
  /**
   * Method used to encrypt the password
   *
   * @param password              password to be encrypted
   * @param secret                secret used to encrypt the password
   * @tparam A                    generic parameter for the password
   * @tparam B                    generic parameter for the secret
   *  @return                     a string representing the password encrypted
   */
  override def encrypt[A,B](password: A, secret: B): String =
    crypto(EncryptionMode.Encryption, password, secret)

  /**
   * Method used to decrypt the password
   *
   * @param encryptedPassword      encrypted password to be decrypted
   * @param secret                 secret used to encrypt the password
   * @tparam A                     generic parameter for the password
   * @tparam B                     generic parameter for the secret
   *  @return                      a string representing the password decrypted
   */
  override def decrypt[A, B](encryptedPassword: A, secret:B): String =
    crypto(EncryptionMode.Decryption, encryptedPassword, secret)

  /**
   * Method used to perform the either the encryption, or the decryption task
   *
   * @param mode                    Mode in with the method must operate, either as a decrypter or an encrypter
   * @param password                Password to be encrypted or decrypted
   * @param secret                  Secred to encrypt or decrypt the password
   * @tparam A                      Generic parameter for the password
   * @tparam B                      Generic parameter for the secret
   * @return                        A string representing the password either encrypted or decrypted
   */
  protected def crypto[A, B](mode:EncryptionMode, password: A, secret: B): String
