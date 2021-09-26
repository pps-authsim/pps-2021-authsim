package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.HashFunction.{SHA1, SHA256, SHA384}
import it.unibo.authsim.library.dsl.Protocol.*
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{OTPPolicyBuilder, PasswordPolicyBuilder, SaltPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, OTPPolicy, PasswordPolicy, SaltPolicy, UserIDPolicy}
import it.unibo.authsim.library.dsl.policy.model.Policy

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*

import scala.language.postfixOps

class PolicyOptionalBuilderTests extends AnyFlatSpec with should.Matchers:

  private val userIDPolicy: CredentialPolicy = UserIDPolicyBuilder() minimumLength 3 build
  private val userIDPolicy1: UserIDPolicy = UserIDPolicyBuilder() minimumLength 5 build

  private val passwordPolicy: CredentialPolicy = PasswordPolicyBuilder() minimumUpperChars 3 minimumLength 8 build
  private val passwordPolicy1: PasswordPolicy = PasswordPolicyBuilder() minimumUpperChars 2 minimumLength 8 build

  private val saltPolicy: SaltPolicy = SaltPolicyBuilder() minimumNumbers 3 minimumLength 5 maximumLength 10 build
  private val saltPolicy1: SaltPolicy = SaltPolicyBuilder() minimumNumbers 7 minimumLength 5 maximumLength 30 build

  private val optPolicy: OTPPolicy = OTPPolicyBuilder() minimumLength 10 maximumLength 10 build
  private val optPolicy1: OTPPolicy = OTPPolicyBuilder() minimumLength 10 maximumLength 30 build

  private val CREDENTIALS_POLICY: Seq[CredentialPolicy] = Seq(passwordPolicy, userIDPolicy)
  private val CREDENTIALS_POLICY_1: Seq[CredentialPolicy] = Seq(passwordPolicy1, userIDPolicy1)
  private val CREDENTIALS_POLICY_2: Seq[CredentialPolicy] = Seq(optPolicy, passwordPolicy1, userIDPolicy1)


  private val policy0: Policy =
    PolicyBuilder("Simple") of (userIDPolicy1, passwordPolicy1) build;

  private val policy1: Policy =
    PolicyBuilder() of (userIDPolicy1, passwordPolicy1) transmitWith Local() build;

  private val policy2: Policy =
    PolicyBuilder("Medium") of userIDPolicy and passwordPolicy storeWith (SHA256(), saltPolicy) transmitWith Https() build;

  private val policy3: Policy =
    PolicyBuilder() of userIDPolicy and passwordPolicy storeWith SHA1() transmitWith Https() build;

  private val policy4: Policy =
    PolicyBuilder("2FA") of (userIDPolicy1, passwordPolicy1) and optPolicy transmitWith Local() build;

  private val policy5: Policy =
    PolicyBuilder("HardOTP") of optPolicy1 storeWith (SHA384(), saltPolicy1) transmitWith Https() build;


  println(policy0)
  println(policy1)
  println(policy2)
  println(policy3)
  println(policy4)
  println(policy5)

  s"Policy '${policy0.name}' was created with with credential policies: $userIDPolicy1 and $passwordPolicy1" should s"contain $CREDENTIALS_POLICY_1" in {
    policy0.credentialPolicies should be (CREDENTIALS_POLICY_1)
  }

  s"A policy was created with with credential policies: $userIDPolicy1 and $passwordPolicy1" should s"contain $CREDENTIALS_POLICY_1" in {
    policy1.credentialPolicies should be (CREDENTIALS_POLICY_1)
  }
  it should "transmitted in Local" in {
    policy1.transmissionProtocol should be (Some(Local()))
  }

  s"Policy '${policy2.name}' was created with with credential policies: $userIDPolicy and $passwordPolicy" should s"contain $CREDENTIALS_POLICY" in {
    policy2.credentialPolicies should be (CREDENTIALS_POLICY)
  }
  it should "transmitted using HTTPS" in {
    policy2.transmissionProtocol should be (Some(Https()))
  }
  it should "stored using SHA256 and Salt" in {
    policy2.hashFunction should be (Some(SHA256()))
    policy2.saltPolicy should be (Some(saltPolicy))
  }

  s"A policy was created with with credential policies: $userIDPolicy and $passwordPolicy" should s"contain $CREDENTIALS_POLICY" in {
    policy3.credentialPolicies should be (CREDENTIALS_POLICY)
  }
  it should "transmitted using HTTPS" in {
    policy3.transmissionProtocol should be (Some(Https()))
  }
  it should "stored using SHA1 " in {
    policy3.hashFunction should be (Some(SHA1()))
  }

  s"Policy '${policy4.name}' was created with with credential policies: $userIDPolicy1, $passwordPolicy1 and $optPolicy" should s"contain $CREDENTIALS_POLICY_2" in {
      policy4.credentialPolicies should be (CREDENTIALS_POLICY_2)
  }
  it should "transmitted in Local" in {
    policy4.transmissionProtocol should be (Some(Local()))
  }

  s"Policy '${policy5.name}' was created with with credential policies: $optPolicy1" should s"contain $optPolicy1" in {
    policy5.credentialPolicies should be (Seq(optPolicy1))
  }
  it should "transmitted using HTTPS" in {
    policy5.transmissionProtocol should be (Some(Https()))
  }
  it should "stored using SHA384 and Salt " in {
    policy5.hashFunction should be (Some(SHA384()))
    policy5.saltPolicy should be (Some(saltPolicy1))
  }