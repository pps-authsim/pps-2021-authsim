package it.unibo.authsim.library.dsl.cryptography
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.symmetric.{AES, DES, CaesarCipher}

import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

trait Encryption:
  def encrypt[A, B](password: A, secret:B): String
  def decrypt[A, B](password: A, secret:B): String

enum EncryptionMode:
  case Decryption, Encryption

trait AsymmetricEncryption extends Encryption with CryptographicAlgorithm

trait CryptographicAlgorithm:
  def algorithmName: String