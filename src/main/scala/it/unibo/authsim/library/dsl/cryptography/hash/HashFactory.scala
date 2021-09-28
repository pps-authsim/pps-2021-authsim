package it.unibo.authsim.library.dsl.cryptography.hash

import it.unibo.authsim.library.dsl.cryptography.{CryptographicAlgorithm}

enum HashFunctionAlgorithm:
  case MD5, SHA1, SHA256, SHA384

object HashAbstractFactory:
  def apply[A>: HashFunction](name: HashFunctionAlgorithm): A=
    name match {
      case HashFunctionAlgorithm.MD5 => new HashFunction.MD5
      case HashFunctionAlgorithm.SHA1=> new HashFunction.SHA1
      case HashFunctionAlgorithm.SHA256 => new HashFunction.SHA256
      case HashFunctionAlgorithm.SHA384 => new HashFunction.SHA384
    }
