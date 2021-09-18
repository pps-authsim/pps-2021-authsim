package it.unibo.authsim.library.dsl.encryption

import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

trait Encryption 

trait SymmetricEncryption extends Encryption:
  def encrypt(password: String, secret:String): String
  def decrypt(password: String, secret:String): String



