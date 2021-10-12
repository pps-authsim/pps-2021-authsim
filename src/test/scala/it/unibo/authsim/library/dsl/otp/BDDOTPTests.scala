package it.unibo.authsim.library.dsl.otp

import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.otp.builders.{HOTPBuilder, TOTPBuilder}
import it.unibo.authsim.library.dsl.otp.model.{HOTP, TOTP}
import it.unibo.authsim.library.dsl.otp.util.OTPHelpers.generatorLength
import it.unibo.authsim.library.dsl.policy.builders.stringpolicy.OTPPolicyBuilder
import it.unibo.authsim.library.dsl.policy.checkers.StringPolicyChecker
import org.scalatest.GivenWhenThen
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.featurespec.AnyFeatureSpec

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.language.postfixOps
import scala.util.Random


class BDDOTPTests extends AnyFeatureSpec with GivenWhenThen:
  private val policy = OTPPolicyBuilder() minimumLength 6 maximumLength 6 build;
  private val sec = ("mario1", "rossi$1")
  private val insertByUser = "123456"

  private val timeout = Duration(5, TimeUnit.SECONDS)
  private val policy1 = OTPPolicyBuilder() minimumLength 7 maximumLength 10 build;
  private val sec1 = ("mario1", "rossi$1")
  private val insertByUser1 = "1234565"

  private var generatedPin: String = null
  private var hotp: HOTP = null
  private var totp: TOTP = null


  feature ("HMAC One Time Password") {
    scenario ("User inserts yours credentials") {
      Given(s" a HOTP that have secret = $sec, hashfunction = ${HashFunction.SHA256.getClass.getSimpleName} and policy = $policy")
      hotp = HOTPBuilder() secret sec withPolicy policy build;
      assert(hotp.hashFunction == HashFunction.SHA256())
      assert(hotp.policy === policy)
      When(" a otp value is generated")
      generatedPin = hotp.generate
      Then("the generated otp should respects the given policy")
      assert(StringPolicyChecker(policy) check generatedPin)
    }

    scenario ("Server checks given otp value") {
      Given("a otp value that respect policy")
      assert(StringPolicyChecker(policy) check insertByUser)
      When( "the given otp is checked")
      And(" it is not valid")
      assert(!hotp.check(insertByUser))
      Then("user must enter another code")

      Given("a otp value that respect policy")
      assert(StringPolicyChecker(policy) check generatedPin)
      When( "the given otp is checked")
      And(" it is valid")
      assert(hotp.check(generatedPin))
      Then("user is logged in")
    }

    scenario("Session is ended"){
      Given("a declared HOTP")
      When("it is resetted")
      hotp.reset
      And("an otp value is generated")
      val regeneratePin = hotp.generate
      Then("the generated otp should be different from the previous one")
      assert(regeneratePin != generatedPin)
    }
  }

  feature ("TIME-BASED One Time Password") {
    scenario ("User inserts yours credentials") {
      Given(s" a TOTP that have timeout = $timeout, secret = $sec1, hashfunction = ${HashFunction.SHA256.getClass.getSimpleName} and policy = $policy1")
      totp = TOTPBuilder() timeout timeout secret sec1 withPolicy policy1 build;
      assert(totp.hashFunction == HashFunction.SHA256())
      assert(totp.policy === policy1)
      assert(totp.timeout === timeout)
      When(" a otp value is generated")
      generatedPin = totp.generate
      Then("the generated otp should respects the given policy")
      assert(StringPolicyChecker(policy1) check generatedPin)
    }
    scenario ("Server checks given otp value") {
      Given("a otp value that respect policy")
      assert(StringPolicyChecker(policy1) check insertByUser1)
      When( "the given otp is checked")
      And(" it is not valid")
      assert(!totp.check(insertByUser1))
      Then("user must enter another code")

      Given("a otp value that respect policy")
      assert(StringPolicyChecker(policy1) check generatedPin)
      When( "the given otp is checked")
      And(" it is valid")
      assert(totp.check(generatedPin))
      Then("user is logged in")
    }
    scenario(s"$timeout have passed"){
      Given("a otp value that respect policy")
      assert(StringPolicyChecker(policy1) check generatedPin)
      When( s"$timeout have passed")
      Thread.sleep(timeout.toMillis)
      Then("the otp value is not valid anymore")
      assert(!totp.check(generatedPin))
    }
  }