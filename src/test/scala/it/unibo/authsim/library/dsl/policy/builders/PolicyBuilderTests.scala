package it.unibo.authsim.library.dsl.policy.builders

import it.unibo.authsim.library.dsl.HashFunction.{SHA1, SHA256, SHA384}
import it.unibo.authsim.library.dsl.Protocol.*
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.builders.StringPolicy.{OTPPolicy, CredentialPolicy, PasswordPolicy, SaltPolicy, UserIDPolicy}
import it.unibo.authsim.library.dsl.policy.model.Policy

import scala.language.postfixOps

class PolicyBuilderTests:

  private val userIDPolicy: CredentialPolicy = (UserIDPolicy() minimumLength 3).asInstanceOf[CredentialPolicy]
  private val userIDPolicy1: UserIDPolicy = (UserIDPolicy() minimumLength 3).asInstanceOf[UserIDPolicy]

  private val passwordPolicy: CredentialPolicy = (PasswordPolicy() minimumUpperChars 3 minimumLength 8).asInstanceOf[CredentialPolicy]
  private val passwordPolicy1: PasswordPolicy = (PasswordPolicy() minimumUpperChars 3 minimumLength 8).asInstanceOf[PasswordPolicy]

  private val saltPolicy: SaltPolicy = (SaltPolicy() minimumNumbers 3 minimumLength 5 maximumLength 10).asInstanceOf[SaltPolicy]
  private val saltPolicy1: SaltPolicy = (SaltPolicy() minimumNumbers 3 minimumLength 5 maximumLength 30).asInstanceOf[SaltPolicy]

  private val optPolicy: OTPPolicy = (OTPPolicy() minimumLength 10 maximumLength 10).asInstanceOf[OTPPolicy]
  private val optPolicy1: OTPPolicy = (OTPPolicy() minimumLength 10 maximumLength 30).asInstanceOf[OTPPolicy]


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