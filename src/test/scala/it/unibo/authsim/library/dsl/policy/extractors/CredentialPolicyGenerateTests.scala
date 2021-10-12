package it.unibo.authsim.library.dsl.policy.extractors

import it.unibo.authsim.library.dsl.policy.builders.*
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.{PasswordPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.extractors.CredentialPolicyGenerate.{PasswordGenerate, UserIDGenerate}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*
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

  private var generatedCredential: Map[String, String] = Map.empty
  import ExtractorTestHelpers.*

  test("Test of extractor generate credential policy (userID, password) string"){
    generatedCredential = getCredentialsTakeyUserIDAndPassword(credetials2)
    println(generatedCredential)
    assert(generatedCredential.size == 2 && generatedCredential.contains("userID") && generatedCredential.contains("password"))
  }

  test("Test of extractor generate credential policy (userID, others) string"){
    generatedCredential = getCredentialsTakeyUserIDAndOthers(credetials2)
    println(generatedCredential)
    assert(generatedCredential.size == 2 && generatedCredential.contains("userID") && generatedCredential.contains("others"))
  }


  test("Test of extractor generate credential policy (password) string"){
    generatedCredential = getCredentialsTakeOnlyPassword(credetialsPass1)
    println(generatedCredential)
    assert(generatedCredential.size == 1 && generatedCredential.contains("password"))
  }

  test("Test of extractor generate credential policy (userID) string"){
    generatedCredential = getCredentialsTakeOnlyUserID(credetialsUser1)
    println(generatedCredential)
    assert(generatedCredential.size == 1 && generatedCredential.contains("userID"))
  }

  private object ExtractorTestHelpers:

    def getCredentialsTakeyUserIDAndPassword(credentialPolicies: Seq[CredentialPolicy]): Map[String, String] =
      credentialPolicies.map {
        case UserIDGenerate(userId) => "userID" -> userId
        case PasswordGenerate(password) => "password" -> password
      }.toMap

    def getCredentialsTakeyUserIDAndOthers(credentialPolicies: Seq[CredentialPolicy]): Map[String, String] =
      credentialPolicies.map {
        case UserIDGenerate(userId) => "userID" -> userId
        case _ => "others" -> ""
      }.toMap

    def getCredentialsTakeOnlyUserID(credentialPolicies: Seq[CredentialPolicy]): Map[String, String] =
        credentialPolicies.map {
          case UserIDGenerate(userId) => "userID" -> userId
        }.toMap

    def getCredentialsTakeOnlyPassword(credentialPolicies: Seq[CredentialPolicy]): Map[String, String] =
      credentialPolicies.map {
        case PasswordGenerate(password) => "password" -> password
      }.toMap
