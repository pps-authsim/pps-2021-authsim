package it.unibo.authsim.library.policy.defaults

import it.unibo.authsim.library.Protocol
import it.unibo.authsim.library.Protocol.*
import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction.*
import it.unibo.authsim.library.policy.builders.PolicyBuilder
import it.unibo.authsim.library.policy.defaults.stringpolicy.*
import it.unibo.authsim.library.policy.model.Policy

import scala.language.postfixOps

trait PolicyDefault:
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Super Simple'' (@see [[UserIDPolicyDefault#SUPER_SIMPLE]]) and
   *  - password policy ''Simple'' (@see [[PasswordPolicyDefault#SIMPLE]])
   */
  def superSimple: Policy
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Simple'' (@see [[UserIDPolicyDefault#SIMPLE]]) and
   *  - password policy ''Simple'' (@see [[PasswordPolicyDefault#SIMPLE]])
   */
  def simple: Policy
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Medium'' (@see [[UserIDPolicyDefault#MEDIUM]]) and
   *  - password policy ''Medium'' (@see [[PasswordPolicyDefault#MEDIUM]])
   */
  def medium: Policy
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Hard'' (@see [[UserIDPolicyDefault#HARD]]) and
   *  - password policy ''Hard'' (@see [[PasswordPolicyDefault#HARD]])
   *
   *  store with [[SHA1 Sha1]]
   */
  def hard: Policy
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Hard'' (@see [[UserIDPolicyDefault#HARD]]) and
   *  - password policy ''Super Hard'' (@see [[PasswordPolicyDefault#SUPER_HARD]])
   *
   *  store with [[SHA256 Sha256]] and salt policy ''Hard'' (@see [[SaltPolicyDefault#HARD]])
   */
  def hardHard: Policy
  /**
   * A Policy with credential policy:
   *
   *  - userID policy ''Hard'' (@see [[UserIDPolicyDefault#HARD]]) and
   *  - password policy ''Super Hard Hard'' (@see [[PasswordPolicyDefault#SUPER_HARD_HARD]]) and
   *  - opt policy ''Hard'' (@see [[OTPPolicyDefault#HARD]])
   *
   *  store with [[SHA384 Sha384]] and salt policy ''Super Hard Hard'' (@see [[SaltPolicyDefault#SUPER_HARD_HARD]])
   */
  def superHardHard: Policy

object PolicyDefault:

  // FOR ATTACKS OFFLINE
  def apply(): PolicyDefault = new PolicyDefaultImpl()

  // FOR ATTACKS ONLINE
  def apply(protocol: Protocol): PolicyDefault = new PolicyDefaultImpl(Some(protocol))

  private class PolicyDefaultImpl(private val protocol: Option[Protocol] = None) extends PolicyDefault:
    private val superSimpleBuilder: PolicyBuilder = PolicyBuilder("SuperSimple") of (UserIDPolicyDefault.SUPER_SIMPLE, PasswordPolicyDefault.SIMPLE)
    private val simpleBuilder: PolicyBuilder = PolicyBuilder("Simple") of (UserIDPolicyDefault.SIMPLE, PasswordPolicyDefault.SIMPLE)
    private val mediumBuilder: PolicyBuilder = PolicyBuilder("Medium") of (UserIDPolicyDefault.MEDIUM, PasswordPolicyDefault.MEDIUM)
    private val hardBuilder: PolicyBuilder = PolicyBuilder("Hard") of (UserIDPolicyDefault.HARD, PasswordPolicyDefault.HARD) storeWith(SHA1())
    private val hardHardBuilder: PolicyBuilder = PolicyBuilder("SuperHard") of (UserIDPolicyDefault.HARD, PasswordPolicyDefault.SUPER_HARD) storeWith (SHA256(), SaltPolicyDefault.HARD)
    private val superHardHardBuilder: PolicyBuilder = PolicyBuilder("SuperHardHard") of (UserIDPolicyDefault.HARD, PasswordPolicyDefault.SUPER_HARD_HARD) and OTPPolicyDefault.HARD storeWith (SHA384(), SaltPolicyDefault.SUPER_HARD_HARD)

    private def addProtocol(policyBuilder: PolicyBuilder): Unit =
      if this.protocol.isDefined then policyBuilder transmitWith this.protocol.get

    override def superSimple: Policy =
      this.addProtocol(superSimpleBuilder);
      superSimpleBuilder build
    override def simple: Policy =
      this.addProtocol(simpleBuilder);
      simpleBuilder build
    override def medium: Policy =
      this.addProtocol(mediumBuilder);
      mediumBuilder build
    override def hard: Policy =
      this.addProtocol(hardBuilder);
      hardBuilder build
    override def hardHard: Policy =
      this.addProtocol(hardHardBuilder);
      hardHardBuilder build
    override def superHardHard: Policy =
      this.addProtocol(superHardHardBuilder);
      superHardHardBuilder build
