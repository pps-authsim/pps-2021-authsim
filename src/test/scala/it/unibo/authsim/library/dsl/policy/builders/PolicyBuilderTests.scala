package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.HashFunction.{SHA1, SHA256, SHA384}
import it.unibo.authsim.library.dsl.Protocol.*
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.{OTPPolicyBuilder, PasswordPolicyBuilder, SaltPolicyBuilder, UserIDPolicyBuilder}
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, OTPPolicy, PasswordPolicy, SaltPolicy, UserIDPolicy}
import it.unibo.authsim.library.dsl.policy.model.Policy

import scala.language.postfixOps

class PolicyBuilderTests:

  private val userIDPolicy: CredentialPolicy = UserIDPolicyBuilder() minimumLength 3 build
  private val userIDPolicy1: UserIDPolicy = UserIDPolicyBuilder() minimumLength 3 build

  private val passwordPolicy: CredentialPolicy = PasswordPolicyBuilder() minimumUpperChars 3 minimumLength 8 build
  private val passwordPolicy1: PasswordPolicy = PasswordPolicyBuilder() minimumUpperChars 3 minimumLength 8 build

  private val saltPolicy: SaltPolicy = SaltPolicyBuilder() minimumNumbers 3 minimumLength 5 maximumLength 10 build
  private val saltPolicy1: SaltPolicy = SaltPolicyBuilder() minimumNumbers 3 minimumLength 5 maximumLength 30 build

  private val optPolicy: OTPPolicy = OTPPolicyBuilder() minimumLength 10 maximumLength 10 build
  private val optPolicy1: OTPPolicy = OTPPolicyBuilder() minimumLength 10 maximumLength 30 build


  private val policy0: Policy =
    PolicyBuilder("Simple") of (userIDPolicy1, passwordPolicy1) build;

  private val policy1: Policy =
    PolicyBuilder() of (userIDPolicy1, passwordPolicy1) transmitWith Local() build;

  private val policy2: Policy =
    PolicyBuilder() of userIDPolicy and passwordPolicy storeWith (SHA256(), saltPolicy) transmitWith Https() build;

  private val policy3: Policy =
    PolicyBuilder() of userIDPolicy and passwordPolicy storeWith SHA1() transmitWith Https() build;

  private val policy4: Policy =
    PolicyBuilder() of (userIDPolicy1, passwordPolicy1) and optPolicy transmitWith Local() build;

  private val policy5: Policy =
    PolicyBuilder("HardOTP") of optPolicy1 storeWith (SHA384(), saltPolicy1) transmitWith Https() build;


  println(policy0)
  println(policy1)
  println(policy2)
  println(policy3)
  println(policy4)
  println(policy5)