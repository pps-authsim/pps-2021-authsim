package it.unibo.authsim.library.dsl.policy.extractors

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.*
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import it.unibo.authsim.library.dsl.policy.extractors.ExtractorCredentialPolicy.{PasswordPolicy, UserIDPolicy}
import it.unibo.authsim.library.dsl.policy.extractors.CredentialPolicyGenerate.{PasswordGenerate, UserIDGenerate}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.CredentialPolicy
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import scala.language.postfixOps

class ExtractorCredentialPolicyTests extends AnyFunSuite with BeforeAndAfter:

  private val credP1: CredentialPolicy = UserIDPolicyBuilder() maximumLength 10 build
  private val credP2: CredentialPolicy = PasswordPolicyBuilder() maximumLength 50 build

  private val credetials = Seq(credP2, credP1)

  private val _userID: String = "mario"
  private val _password: String = "mariorossi$1111"

  test("Test of extractor effective credential policy"){
    assert(credetials.map(cred => cred match
      case UserIDPolicy(userIDPolicy) => StringPolicyChecker(userIDPolicy) check _userID
      case PasswordPolicy(passwordPolicy) => StringPolicyChecker(passwordPolicy) check _password
      case _ => false
    ) == Seq.fill(2)(true))
  }

  test("Test of extractor generate credential policy string"){
    val generatedCredential: Map[String, String] = credetials.map(cred => cred match
      case UserIDGenerate(userId) => "userID" -> userId
      case PasswordGenerate(password) => "password" -> password
      case _ => "others" -> ""
    ).toMap
    println(generatedCredential)
    assert(generatedCredential.size == 2 && generatedCredential.contains("userID") && generatedCredential.contains("password"))
  }
