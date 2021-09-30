package it.unibo.authsim.library.dsl.cryptography
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.symmetric.{AES, DES, CaesarCipher}

import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

/**
 * Trait that represent a basic encryption
 */
trait Encryption:
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
 * Trait that represent a crypthographic algorithm
 */
trait CryptographicAlgorithm:
  /**
   * Method used to get the name of the crypthographic algorithm
   * @return                        a string representing the name of crypthographic algorithm
   */
  def algorithmName: String

/**
 * Abstract class for to create encryption algorithms
 */
abstract class BasicEcryption extends Encryption with CryptographicAlgorithm:
  /**
   * Variable representing the name of the algorithm
   */
  protected val _name: String

  /**
   * Method used to encrypt the password
   *
   * @param password      password to be encrypted
   * @param secret        secret used to encrypt the password
   * @tparam A            generic parameter for the password
   * @tparam B            generic parameter for the secret
   *  @return              a string representing the password encrypted
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
   *  @return                       a string representing the password decrypted
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
  def crypto[A, B](mode:EncryptionMode, password: A, secret: B): String

  /**
   * Getter for the algorithm name
   *  @return                       A string representing the name of crypthographic algorithm
   */
  override def algorithmName: String = _name

  /**
   * To string method for the object
   *
   * @return                         A string representing the name of crypthographic algorithm
   */
  override def toString: String = _name

