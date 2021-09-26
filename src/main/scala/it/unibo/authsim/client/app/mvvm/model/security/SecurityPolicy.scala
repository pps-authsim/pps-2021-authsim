package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.client.app.mvvm.model.security
import it.unibo.authsim.library.dsl.Protocol.*
import it.unibo.authsim.library.dsl.policy.defaults.PolicyDefault
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.CredentialPolicy

case class SecurityPolicy(val policy: String, val description: String)

object SecurityPolicy:

  object Default extends Enumeration:
    type Default = Value

    protected case class DefaultVal(val policy: Policy, val description: String) extends super.Val:
      def name: String = (if policy.transmissionProtocol.isDefined then s"${policy.transmissionProtocol.get.toString.replace("()", "")}-" else "") + policy.name
      override def compare(that: SecurityPolicy.Default.Value): Int = this.name compare that.name
      override def toString: String = s"${policy.name} - ${description}"

    import scala.language.implicitConversions
    implicit def valueToDefaultVal(x: Value): DefaultVal = x.asInstanceOf[DefaultVal]

    private val policiesDefaults = PolicyDefault()
    private val policiesDefaultsHTTP = PolicyDefault(Http())
    private val policiesDefaultsHTTPS = PolicyDefault(Https())
    private val policiesDefaultsSSH = PolicyDefault(Ssh())

    //TODO: add description policy
    val SUPERSIMPLE = DefaultVal(policiesDefaults.superSimple, "")
    val SIMPLE = DefaultVal(policiesDefaults.simple, "")
    val MEDIUM = DefaultVal(policiesDefaults.medium, "")
    val HARD = DefaultVal(policiesDefaults.hard, "")
    val HARDHARD = DefaultVal(policiesDefaults.hardHard, "")
    val SUPERHARDHARD = DefaultVal(policiesDefaults.superHardHard, "")

    val HTTP_SUPERSIMPLE = DefaultVal(policiesDefaultsHTTP.superSimple, "")
    val HTTP_SIMPLE = DefaultVal(policiesDefaultsHTTP.simple, "")
    val HTTP_MEDIUM = DefaultVal(policiesDefaultsHTTP.medium, "")
    val HTTP_HARD = DefaultVal(policiesDefaultsHTTP.hard, "")
    val HTTP_HARDHARD = DefaultVal(policiesDefaultsHTTP.hardHard, "")
    val HTTP_SUPERHARDHARD = DefaultVal(policiesDefaultsHTTP.superHardHard, "")

    val HTTPS_SUPERSIMPLE = DefaultVal(policiesDefaultsHTTPS.superSimple, "")
    val HTTPS_SIMPLE = DefaultVal(policiesDefaultsHTTPS.simple, "")
    val HTTPS_MEDIUM = DefaultVal(policiesDefaultsHTTPS.medium, "")
    val HTTPS_HARD = DefaultVal(policiesDefaultsHTTPS.hard, "")
    val HTTPS_HARDHARD = DefaultVal(policiesDefaultsHTTPS.hardHard, "")
    val HTTPS_SUPERHARDHARD = DefaultVal(policiesDefaultsHTTPS.superHardHard, "")

    val SSH_SUPERSIMPLE = DefaultVal(policiesDefaultsSSH.superSimple, "")
    val SSH_SIMPLE = DefaultVal(policiesDefaultsSSH.simple, "")
    val SSH_MEDIUM = DefaultVal(policiesDefaultsSSH.medium, "")
    val SSH_HARD = DefaultVal(policiesDefaultsSSH.hard, "")
    val SSH_HARDHARD = DefaultVal(policiesDefaultsSSH.hardHard, "")
    val SSH_SUPERHARDHARD = DefaultVal(policiesDefaultsSSH.superHardHard, "")

    def withoutProtocol: SecurityPolicy.Default.ValueSet = this.values.filter(_.policy.transmissionProtocol.isEmpty)

    def all: Seq[SecurityPolicy] = (for default <- this.values.toSeq yield SecurityPolicy(default.name, default.description))

    def credentialsPoliciesFrom(name: String): Option[Seq[CredentialPolicy]] =
      val default = this.values.find(_.name == name)
      if default.isDefined then Some(default.get.policy.credentialPolicies) else None