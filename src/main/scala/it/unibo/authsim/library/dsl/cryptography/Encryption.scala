package it.unibo.authsim.library.dsl.cryptography
import it.unibo.authsim.library.dsl.cryptography.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.symmetric.{AES, DES, CaesarCipher}

import java.security.KeyPair
import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

trait Encryption:
  def encrypt(password: String, secret:String): String
  def decrypt(password: String, secret:String): String

enum EncryptionMode:
  case Decryption, Encryption

trait AsymmetricEncryption extends Encryption with CryptographicAlgorithm
/*
trait HashFunction extends CryptographicAlgorithm:
  def hash(str: String): String
  
*/
trait CryptographicAlgorithm:
  def algorithmName: String

trait Keys:
  def publicKey: String
  def privateKey: String

trait KeyGenerator:
  def generateKeys(): Keys
  def loadKeys(fileName: String):Keys
