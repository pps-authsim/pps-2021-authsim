package it.unibo.authsim.library.policy.extractors

import it.unibo.authsim.library.policy.builders.*
import it.unibo.authsim.library.policy.builders.stringpolicy.{PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.policy.extractors.CredentialPolicyGenerate.{PasswordGenerate, UserIDGenerate}
import it.unibo.authsim.library.policy.model.StringPolicies.*
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import scala.language.postfixOps

class CredentialPolicyGenerateTests extends AnyFunSuite with BeforeAndAfter:

  private val credP1: CredentialPolicy = UserIDPolicyBuilder() maximumLength 10 build
  private val credP2: CredentialPolicy = PasswordPolicyBuilder() maximumLength 50 build

  private val credetials2 = Seq(credP2, credP1)
  private val credetialsPass1 = Seq(credP2)
  private val credetialsUser1 = Seq(credP1)

  private val _userID: String = "mario"
  private val _password: String = "mariorossi$1111"

  test("Test of extractor generate credential policy (userID, password) string"){
    val generatedCredential: Map[String, String] = credetials2.map(cred => cred match
      case UserIDGenerate(userId) => "userID" -> userId
      case PasswordGenerate(password) => "password" -> password
      case _ => "others" -> ""
    ).toMap
    println(generatedCredential)
    assert(generatedCredential.size == 2 && generatedCredential.contains("userID") && generatedCredential.contains("password"))
  }

  test("Test of extractor generate credential policy (userID, others) string"){
    val generatedCredential: Map[String, String] = credetials2.map(cred => cred match
      case UserIDGenerate(userId) => "userID" -> userId
      case _ => "others" -> "random"
    ).toMap
    println(generatedCredential)
    assert(generatedCredential.size == 2 && generatedCredential.contains("userID") && generatedCredential.contains("others"))
  }


  test("Test of extractor generate credential policy (password) string"){
    val generatedCredential: Map[String, String] = credetialsPass1.map(cred => cred match
      case PasswordGenerate(password) => "password" -> password
      case _ => "others" -> ""
    ).toMap
    println(generatedCredential)
    assert(generatedCredential.size == 1 && generatedCredential.contains("password"))
  }

  test("Test of extractor generate credential policy (userID) string"){
    val generatedCredential: Map[String, String] = credetialsUser1.map(cred => cred match
      case UserIDGenerate(userId) => "userID" -> userId
      case _ => "others" -> ""
    ).toMap
    println(generatedCredential)
    assert(generatedCredential.size == 1 && generatedCredential.contains("userID"))
  }
