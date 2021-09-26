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

trait SymmetricEncryption extends Encryption with CryptographicAlgorithm

trait AsymmetricEncryption extends Encryption with CryptographicAlgorithm

trait CryptographicAlgorithm:
  def algorithmName: String

trait Keys:
  def publicKey: String
  def privateKey: String

trait KeyGenerator:
  def generateKeys(): Keys
  def loadKeys(fileName: String):Keys
  
enum EncryptionMode:
    case Decryption, Encryption

enum SymmetricEncryptionAlgorithm:
  case CaesarCipher, AES, DES, RSA

enum HashFunctionAlgorithm:
  case MD5, SHA1, SHA256, SHA384

object SymmetricEncryptionAbstractFactory:
  def apply[A>: Encryption ](name: SymmetricEncryptionAlgorithm): A =
    name match {
      case SymmetricEncryptionAlgorithm.CaesarCipher => CaesarCipher()
      case SymmetricEncryptionAlgorithm.AES => AES()
      case SymmetricEncryptionAlgorithm.DES => DES()
    }

object HashAbstractFactory:
  def apply[A>: HashFunction](name: HashFunctionAlgorithm): A=
    name match {
      case HashFunctionAlgorithm.MD5 => new HashFunction.MD5
      case HashFunctionAlgorithm.SHA1=> new HashFunction.SHA1
      case HashFunctionAlgorithm.SHA256 => new HashFunction.SHA256
      case HashFunctionAlgorithm.SHA384 => new HashFunction.SHA384
    }

object Prova:
  def main(args: Array[String]): Unit =
   val prova= SymmetricEncryptionAbstractFactory(SymmetricEncryptionAlgorithm.CaesarCipher)
   val prova2:HashFunction=HashAbstractFactory(HashFunctionAlgorithm.MD5)
   println("prova "+ prova)