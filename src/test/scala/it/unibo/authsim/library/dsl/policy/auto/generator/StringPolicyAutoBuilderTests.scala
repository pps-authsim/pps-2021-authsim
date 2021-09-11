package it.unibo.authsim.library.dsl.policy.auto.generator

import it.unibo.authsim.library.dsl.policy.builders.StringPolicy.*
import org.scalatest.*
import org.scalatest.funsuite.AnyFunSuite

class StringPolicyAutoBuilderTests extends AnyFunSuite with BeforeAndAfter:

  private var userID = UserIDPolicy()
  private var password = PasswordPolicy()
  private var otp = OTPPolicy()

  import StringPolicyAutoBuilderTestHelpers.*

  before{
    userID = UserIDPolicy()
    password = PasswordPolicy()
    otp = OTPPolicy()
  }

  test("UserID was generated from a policy formed by at least 3 uppercase characters and between 5 and 8 characters long"){
    assert(testAutoBuilder(userID minimumUpperChars 3 minimumLength 5 maximumLength 8))
  }

  test("UserID was generated from a policy formed by at least 2 symbols, 1 uppercase characters and between 3 and 10 characters long"){
    assert(testAutoBuilder(userID minimumSymbols 2 minimumUpperChars 1 minimumLength 3 maximumLength 10))
  }

  test("Password was generated from a policy formed by between 10 and 30 characters long"){
    assert(testAutoBuilder(password minimumLength 10 maximumLength 30))
  }

  test("Password was generated from a policy formed by at least 2 symbols, 3 uppercase characters, 4 numbers and between 4 and 10 characters long"){
    assert(testAutoBuilder(password minimumSymbols 2 minimumUpperChars 3 minimumNumbers 4 minimumLength 4 maximumLength 10))
  }

  test("Password was generated from a policy formed by at least 4 numbers and between 1 and 7 characters long"){
    assert(testAutoBuilder(password minimumNumbers 4 maximumLength 7))
  }

  test("OTP was generated from a policy formed by between 4 and 11 characters long"){
    assert(testAutoBuilder(otp minimumLength 4 maximumLength 11))
  }

  test("Policies (userID, password) was generate from sequence of policy: " +
    "- UserID formed by at least 1 symbols, 1 uppercase characters, 4 numbers and between 3 and 10 characters long" +
    "- Password formed by at least 2 symbols and between 4 and 7 characters long"){
    assert(
      testAutoBuilder(
        Seq(userID minimumSymbols 1 minimumUpperChars 1 minimumLength 3 maximumLength 10,
            password minimumSymbols 2 minimumNumbers 4 maximumLength 7)
      )
    )
  }

  private object StringPolicyAutoBuilderTestHelpers:
    import scala.language.postfixOps
    import it.unibo.authsim.library.dsl.policy.builders.StringPolicy.Builder
    import it.unibo.authsim.library.dsl.policy.checker.StringPolicyChecker

    private var generatedPolicy: String = ""
    private var generatedPolicies: Seq[String] = Seq.empty

    def testAutoBuilder(policy: Builder): Boolean =
      generatedPolicy = StringPolicyAutoBuilder(policy) generate;
      println(generatedPolicy)
      StringPolicyChecker(policy) check generatedPolicy

    def testAutoBuilder(policies: Seq[Builder]): Boolean =
      generatedPolicies = StringPolicyAutoBuilder(policies) generate;
      println(generatedPolicies)
      generatedPolicies.zipWithIndex.forall((generatedPolicy, index) => StringPolicyChecker(policies(index)) check generatedPolicy)

