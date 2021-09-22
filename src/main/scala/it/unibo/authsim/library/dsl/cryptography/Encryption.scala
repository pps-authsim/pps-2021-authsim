package it.unibo.authsim.library.dsl.cryptography

import java.security.KeyPair
import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

trait Encryption:
  def encrypt(password: String, secret:String): String
  def decrypt(password: String, secret:String): String

trait SymmetricEncryption extends Encryption

trait AsymmetricEncryption extends Encryption

trait CryptographicAlgorithm:
  def algorithmName: String

trait Keys:
  def publicKey: String
  def privateKey: String

trait KeyGenerator extends Keys with CryptographicAlgorithm :
  def generateKeys(): Keys
  def algorithmName_(algorithmName:String): Unit
  
trait PersistentKeyGenerator extends KeyGenerator:
  def loadOrCreate(fileName: String): Keys

enum EncryptionMode:
    case Decryption, Encryption

 



