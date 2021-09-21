package it.unibo.authsim.library.dsl.policy.default

import it.unibo.authsim.library.dsl.Protocol.*
import it.unibo.authsim.library.dsl.HashFunction.*
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.Policy

import scala.language.postfixOps

object PolicyDefault:

  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Super Simple'' (@see [[UserIDPolicyDefault#SUPER_SIMPLE]]) and
   *  - password policy ''Simple'' (@see [[PasswordPolicyDefault#SIMPLE]])
   *
   *  transmit with [[Http HTTP]] protocol
   */
  val SUPER_SIMPLE: Policy = PolicyBuilder("SuperSimple") of (UserIDPolicyDefault.SUPER_SIMPLE, PasswordPolicyDefault.SIMPLE) transmitWith Http() build
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Simple'' (@see [[UserIDPolicyDefault#SIMPLE]]) and
   *  - password policy ''Simple'' (@see [[PasswordPolicyDefault#SIMPLE]])
   *
   *  transmit with [[Local Local]] protocol
   */
  val SIMPLE: Policy = PolicyBuilder("Simple") of (UserIDPolicyDefault.SIMPLE, PasswordPolicyDefault.SIMPLE) transmitWith Local() build
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Medium'' (@see [[UserIDPolicyDefault#MEDIUM]]) and
   *  - password policy ''Medium'' (@see [[PasswordPolicyDefault#MEDIUM]])
   *
   *  transmit with [[Ssh SSH]] protocol
   */
  val MEDIUM: Policy = PolicyBuilder("Medium") of (UserIDPolicyDefault.MEDIUM, PasswordPolicyDefault.MEDIUM) transmitWith Ssh() build
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Hard'' (@see [[UserIDPolicyDefault#HARD]]) and
   *  - password policy ''Hard'' (@see [[PasswordPolicyDefault#HARD]])
   *
   *  transmit with [[Local Local]] protocol and store with [[SHA1 Sha1]]
   */
  val HARD: Policy = PolicyBuilder("Hard") of (UserIDPolicyDefault.HARD, PasswordPolicyDefault.HARD) transmitWith Local() storeWith(SHA1()) build
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Hard'' (@see [[UserIDPolicyDefault#HARD]]) and
   *  - password policy ''Super Hard'' (@see [[PasswordPolicyDefault#SUPER_HARD]])
   *
   *  transmit with [[Ssh SSH]] protocol and store with [[SHA256 Sha256]] and salt policy ''Hard'' (@see [[SaltPolicyDefault#HARD]])
   */
  val SUPER_HARD: Policy = PolicyBuilder("SuperHard") of (UserIDPolicyDefault.HARD, PasswordPolicyDefault.SUPER_HARD) transmitWith Ssh() storeWith (SHA256(), SaltPolicyDefault.HARD) build
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Hard'' (@see [[UserIDPolicyDefault#HARD]]) and
   *  - password policy ''Super Hard Hard'' (@see [[PasswordPolicyDefault#SUPER_HARD_HARD]]) and
   *  - opt policy ''Hard'' (@see [[OTPPolicyDefault#HARD]])
   *
   *  transmit with [[Https HTTPS]] protocol and store with [[SHA384 Sha384]] and salt policy ''Super Hard Hard'' (@see [[SaltPolicyDefault#SUPER_HARD_HARD]])
   */
  val SUPER_HARD_HARD: Policy = PolicyBuilder("SuperHardHard") of (UserIDPolicyDefault.HARD, PasswordPolicyDefault.SUPER_HARD_HARD) and OTPPolicyDefault.HARD transmitWith Https() storeWith (SHA384(), SaltPolicyDefault.SUPER_HARD_HARD) build