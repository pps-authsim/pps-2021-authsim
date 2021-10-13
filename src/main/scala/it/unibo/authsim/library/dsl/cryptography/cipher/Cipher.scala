package it.unibo.authsim.library.dsl.cryptography.cipher

import it.unibo.authsim.library.dsl.cryptography.algorithm.EncryptionAlgorithm
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.key.KeyPair

import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

/**
 * Trait that represents a basic cipher.
 */
trait Cipher:
  def algorithm : EncryptionAlgorithm

  /**
   * Enumeration that specify the mode in which the cipher should work, either to encrypt or decrypt a password.
   */
  protected enum EncryptionMode:
    case Decryption, Encryption

  /**
   * Method used to encrypt a password using a secret.
   *
   * @param password : password to be encrypted
   * @param secret : secret used to encrypt the password
   * @tparam A : generic parameter for the password
   * @tparam B : generic parameter for the secret
   * @return : a string representing the password encrypted
   */
  def encrypt[A, B](password: A, secret:B): String

  /**
   * Method used to decrypt a password using a secret.
   *
   * @param encryptedPassword : encrypted password to be decrypted
   * @param secret : secret used to decrypt the password
   * @tparam A : generic parameter for the password
   * @tparam B : generic parameter for the secret
   * @return : a string representing the password decrypted
   */
  def decrypt[A, B](encryptedPassword: A, secret:B): String
  
/**
 * Trait that models a symmetric cipher.
 */
trait SymmetricCipher extends Cipher

/**
 * Trait that models an asymmetric cipher, it provides additional methods to manage the encryption operation
 * using asymmetric encryption algorithms.
 */
trait AsymmetricCipher extends Cipher:
  
  /**
   * Method to load existing key from a user directory.
   * 
   * @param fileName : name of the file that should store the key
   * @return : an istance of KeyPair
   */
  def loadKeys(fileName: String):KeyPair

  /**
   * Method to generate a new key pair and to save it on the disk, overriding prexisting information optionally present in the file.
   * 
   * @param fileName : name of the file where the key pair should be saved
   * @return : the key pair generated
   */
  def generateKeys(fileName: String): KeyPair

/**
 * Abstract class to model a cipher.
 */
abstract class BasicCipher extends Cipher:
  
  /**
   * Method used to encrypt the password.
   *
   * @param password : password to be encrypted
   * @param secret : secret used to encrypt the password
   * @tparam A : generic parameter for the password
   * @tparam B  : generic parameter for the secret
   *  @return : a string representing the password encrypted
   */
  override def encrypt[A,B](password: A, secret: B): String =
    crypto(EncryptionMode.Encryption, password, secret)

  /**
   * Method used to decrypt the password.
   *
   * @param encryptedPassword : encrypted password to be decrypted
   * @param secret : secret used to encrypt the password
   * @tparam A : generic parameter for the password
   * @tparam B : generic parameter for the secret
   *  @return : a string representing the password decrypted
   */
  override def decrypt[A, B](encryptedPassword: A, secret:B): String =
    crypto(EncryptionMode.Decryption, encryptedPassword, secret)

  /**
   * Method used to perform either the encryption, or the decryption operation.
   *
   * @param mode : mode in with the method must operate, either as a decrypter or an encrypter
   * @param password : password to be encrypted or decrypted
   * @param secret : secred to encrypt or, decrypt the password
   * @tparam A : generic parameter for the password
   * @tparam B : generic parameter for the secret
   * @return : a string representing the password either encrypted or decrypted
   */
  protected def crypto[A, B](mode:EncryptionMode, password: A, secret: B): String
  
  