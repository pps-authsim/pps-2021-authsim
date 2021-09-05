package it.unibo.authsim.library.dsl

import it.unibo.authsim.library.dsl.HashFunction
import org.scalatest.wordspec.AnyWordSpec

class HashFunctionTest extends AnyWordSpec {
  var password="foo"
  var hashFunctionSHA = new HashFunction.SHA
  var hashFunctionMD5 = new HashFunction.MD5
  var hashedValueSHA = "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33"
  var hashedValueMD5 = "acbd18db4cc2f85cedef654fccc4a4d8"

  "A Password hashed with SHA algorithm" should{
    "returned the hashed value" in {
      assert(hashFunctionSHA.hash(password) == hashedValueSHA)

    }
  }

  "A Password hashed with MD5 algorithm" should{
    "returned the hashed value" in {
      assert(hashFunctionMD5.hash(password) == hashedValueMD5)
    }
  }

}
