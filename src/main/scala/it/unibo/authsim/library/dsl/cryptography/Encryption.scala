package it.unibo.authsim.library.dsl.cryptography
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.symmetric.{AES, DES, CaesarCipher}

import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

trait Encryption:
  protected enum EncryptionMode:
    case Decryption, Encryption

  def encrypt[A, B](password: A, secret:B): String
  def decrypt[A, B](password: A, secret:B): String

trait CryptographicAlgorithm:
  def algorithmName: String

abstract class BasicEcryption extends Encryption with CryptographicAlgorithm:
  
  protected val _name: String
  
  override def encrypt[A,B](password: A, secret: B): String =
    crypto(EncryptionMode.Encryption, password, secret)

  override def decrypt[A, B](encryptedPassword: A, secret:B): String =
    crypto(EncryptionMode.Decryption, encryptedPassword, secret)

  def crypto[A, B](mode:EncryptionMode, password: A, secret: B): String

  override def algorithmName: String = _name

  override def toString: String = _name

