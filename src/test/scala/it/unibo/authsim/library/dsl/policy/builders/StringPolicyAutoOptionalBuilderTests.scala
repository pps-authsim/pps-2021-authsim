package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.*
import org.scalatest.*
import org.scalatest.funsuite.AnyFunSuite

import scala.language.postfixOps

class StringPolicyAutoOptionalBuilderTests extends AnyFunSuite with BeforeAndAfter:

  private var userID = UserIDPolicyBuilder()
  private var password = PasswordPolicyBuilder()
  private var otp = OTPPolicyBuilder()

  import StringPolicyAutoBuilderTestHelpers.*

  before{
    userID = UserIDPolicyBuilder()
    password = PasswordPolicyBuilder()
    otp = OTPPolicyBuilder()
  }

  test("UserID was generated from a policy formed by at least 3 uppercase characters and between 5 and 8 characters long"){
    assert(testAutoBuilder(userID minimumUpperChars 3 minimumLength 5 maximumLength 8 build))
  }

  test("UserID was generated from a policy formed by at least 2 symbols, 1 uppercase characters and between 3 and 10 characters long"){
    assert(testAutoBuilder(userID minimumSymbols 2 minimumUpperChars 1 minimumLength 3 maximumLength 10 build))
  }

  test("Password was generated from a policy formed by between 10 and 30 characters long"){
    assert(testAutoBuilder(password minimumLength 10 maximumLength 30 build))
  }

  test("Password was generated from a policy formed by at least 2 symbols, 3 uppercase characters, 4 numbers and between 4 and 10 characters long"){
    assert(testAutoBuilder(password minimumSymbols 2 minimumUpperChars 3 minimumNumbers 4 minimumLength 4 maximumLength 10 build))
  }

  test("Password was generated from a policy formed by at least 4 numbers and between 1 and 7 characters long"){
    assert(testAutoBuilder(password minimumNumbers 4 maximumLength 7 build))
  }

  test("OTP was generated from a policy formed by between 4 and 11 characters long"){
    assert(testAutoBuilder(otp minimumLength 4 maximumLength 11 build))
  }

  test("Policies (userID, password) was generate from sequence of policy: " +
    "- UserID formed by at least 1 symbols, 1 uppercase characters, 4 numbers and between 3 and 10 characters long" +
    "- Password formed by at least 2 symbols and between 4 and 7 characters long"){
    assert(
      testAutoBuilder(
        Seq(userID minimumSymbols 1 minimumUpperChars 1 minimumLength 3 maximumLength 10 build,
            password minimumSymbols 2 minimumNumbers 4 maximumLength 7 build)
      )
    )
  }

  private object StringPolicyAutoBuilderTestHelpers:
    import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
    import it.unibo.authsim.library.dsl.policy.model.StringPolicies.StringPolicy

    import scala.language.postfixOps

    private var generatedPolicy: String = ""
    private var generatedPolicies: Seq[String] = Seq.empty

    def testAutoBuilder(policy: StringPolicy): Boolean =
      generatedPolicy = policy generate;
      println(generatedPolicy)
      StringPolicyChecker(policy) check generatedPolicy

    def testAutoBuilder(policies: Seq[StringPolicy]): Boolean =
      generatedPolicies = policies.map { _.generate }
      println(generatedPolicies)
      generatedPolicies.zipWithIndex.forall((generatedPolicy, index) => StringPolicyChecker(policies(index)) check generatedPolicy)

