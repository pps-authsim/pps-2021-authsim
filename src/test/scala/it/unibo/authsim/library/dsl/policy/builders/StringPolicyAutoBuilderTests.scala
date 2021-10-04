package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.alphabet.SymbolicAlphabet
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.*
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{RestrictStringPolicy, StringPolicy}
import org.scalatest.*
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListSet
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps
import scala.util.matching.Regex

class StringPolicyAutoBuilderTests extends AnyFunSuite with BeforeAndAfter:

  private var userID = UserIDPolicyBuilder()
  private var password = PasswordPolicyBuilder()
  private var otp = OTPPolicyBuilder()

  private var myPolicy: StringPolicy = new StringPolicy {
    override def alphabet: PolicyAlphabet = new PolicyAlphabet {
      override def lowers = SymbolicAlphabet(ListSet("a", "e", "i", "o", "u"))
      override def uppers = SymbolicAlphabet(this.lowers.map { _.toUpperCase })
      override def digits = SymbolicAlphabet()
      override def symbols = SymbolicAlphabet()
    }
    override def patterns: ListBuffer[Regex] = ListBuffer.empty
  }

  private var myRestrictedPolicy: StringPolicy = new StringPolicy with RestrictStringPolicy {
    override def alphabet: PolicyAlphabet = new PolicyAlphabet {
      override def lowers = SymbolicAlphabet(ListSet("c", "e", "i", "o", "u"))
      override def uppers = SymbolicAlphabet(this.lowers.map { _.toUpperCase })
      override def digits = SymbolicAlphabet(ListSet("0", "3"))
      override def symbols = SymbolicAlphabet()
    }
    override def patterns: ListBuffer[Regex] = ListBuffer.empty

    override def minimumLength: Int = 3

    override def maximumLength: Int = 10
  }

  import StringPolicyAutoBuilderTestHelpers.*

  before{
    userID = UserIDPolicyBuilder()
    password = PasswordPolicyBuilder()
    otp = OTPPolicyBuilder()
  }

  test("A String was created from myPolicy formed by between 1 and 30 characters long and an alphabet contains the following characters: a e i o u A E I O U"){
    assert(testAutoBuilder(myPolicy))
  }

  test("A String was created from myRestrictedPolicy formed by between 3 and 10 characters long and an alphabet contains the following characters: c e i o u C  E I O U 0 3"){
    assert(testAutoBuilder(myRestrictedPolicy))
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

