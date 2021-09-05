package it.unibo.authsim.library.dsl

import it.unibo.authsim.library.dsl.HashFunction
import org.junit.Assert.{assertEquals, assertNotEquals}
import org.junit.Test

class HashFunctionTest {
  var password="foo"
  var hashFunctionSHA = new HashFunction.SHA
  var hashFunctionMD5 = new HashFunction.MD5
  var hashedValueSHA = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae"
  var hashedValueMD5 = "acbd18db4cc2f85cedef654fccc4a4d8"

  @Test def TestHashFunctionSHA(): Unit = {
    assertNotEquals("",  hashFunctionSHA.hash(password))
    assertEquals(hashedValueSHA, hashFunctionSHA.hash(password))
  }

  @Test def TestHashFunctionMD5(): Unit = {
    assertNotEquals("",  hashFunctionMD5.hash(password))
    assertEquals(hashedValueMD5, hashFunctionMD5.hash(password))
  }

}
