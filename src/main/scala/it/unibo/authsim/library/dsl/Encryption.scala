package it.unibo.authsim.library.dsl

import java.security.spec.{AlgorithmParameterSpec, KeySpec}
import java.util.Base64
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, PBEParameterSpec}

trait Encryption 

trait SymmetricEncryption extends Encryption:
  def encrypt(password: String, secret:String): String
  def decrypt(password: String, secret:String): String


