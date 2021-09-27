package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.client.app.mvvm.model.security
import it.unibo.authsim.library.dsl.Protocol
import it.unibo.authsim.library.dsl.Protocol.*
import it.unibo.authsim.library.dsl.policy.defaults.PolicyDefault
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.CredentialPolicy

case class SecurityPolicy(val policy: String, val description: String)

object SecurityPolicy:

  object Default extends Enumeration:
    type Default = Value

    implicit class Descriptor(base: String):
      def descriptor(defaultVal: DefaultVal) =
        s"""${base}\n
            ${if defaultVal.policy.transmissionProtocol.isDefined then s"Credentials are trasmitted with protocol ${defaultVal.policy.transmissionProtocol.get.toString.replace("()", "").toUpperCase}." else "No protocol." }\n
            The alphabet:  ${defaultVal.policy.credentialPolicies.map(_.alphabet).map(a => a.lowers.concat(a.uppers).concat(a.digits).concat(a.symbols).mkString).distinct.mkString}"""

    protected case class DefaultVal(val policy: Policy) extends super.Val:
      def name: String = (if policy.transmissionProtocol.isDefined then s"${policy.transmissionProtocol.get.toString.replace("()", "")}-" else "") + policy.name
      def description: String = this.policy.name match
        case "SuperSimple" =>
            """
             An userID has
              - minimum length of 3 characters
              - maximum of 20 characters

             A password has
              - minimum length of 3 characters and
              - maximum of 20 characters

            Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)
        case "Simple" =>
            """
             An userID has
              - minimum length of 8 characters
              - maximum of 20 characters

             A password has
              - minimum length of 3 characters and
              - maximum of 20 characters

             Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)
        case "Medium" =>
            """
             An userID has
              - minimum length of 8 characters
              - maximum of 20 characters
              - a minimum of 1 symbols

             A password has
              - minimum length of 8 characters and
              - maximum of 20 characters

             Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)
        case "Hard" =>
            """
             An userID has
              - minimum length of 10 characters
              - maximum of 20 characters
              - a minimum of 2 symbols
              - a minimum of 3 uppercase characters

             A password has
              - minimum length of 8 characters and
              - maximum of 20 characters
              - a minimum of 1 symbols

             Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)
        case "SuperHard" =>
            """
             An userID has
              - minimum length of 10 characters
              - maximum of 20 characters
              - a minimum of 2 symbols
              - a minimum of 3 uppercase characters

             A password has
              - minimum length of 8 characters and
              - maximum of 20 characters
              - a minimum of 1 symbols
              - a minimum of 3 uppercase characters

            The userID is stored in plain text and password is stored with SHA256 algoritm using salt value with
              - minimum length of 8 characters
              - maximum of 20 characters
              - a minimum of 1 symbols
              - a minimum of 1 uppercase characters
            in the database.""".descriptor(this)
        case "SuperHardHard" =>
            """
               An userID has
                - minimum length of 10 characters
                - maximum of 20 characters
                - a minimum of 2 symbols
                - a minimum of 3 uppercase characters

               A password has
                - minimum length of 10 characters and
                - maximum of 20 characters
                - a minimum of 2 symbols
                - a minimum of 3 uppercase characters

              The userID is stored in plain text and password is stored with SHA384 algoritm using salt value with
                - minimum length of 10 characters
                - maximum of 20 characters
                - a minimum of 2 symbols
                - a minimum of 3 uppercase characters
              in the database.""".descriptor(this)

      override def compare(that: SecurityPolicy.Default.Value): Int = this.name compare that.name
      override def toString: String = s"${policy.name} - ${description}"

    import scala.language.implicitConversions
    implicit def valueToDefaultVal(x: Value): DefaultVal = x.asInstanceOf[DefaultVal]

    private val policiesDefaults = PolicyDefault()
    private val policiesDefaultsHTTP = PolicyDefault(Http())
    private val policiesDefaultsHTTPS = PolicyDefault(Https())
    private val policiesDefaultsSSH = PolicyDefault(Ssh())

    val SUPERSIMPLE = DefaultVal(policiesDefaults.superSimple)
    val SIMPLE = DefaultVal(policiesDefaults.simple)
    val MEDIUM = DefaultVal(policiesDefaults.medium)
    val HARD = DefaultVal(policiesDefaults.hard)
    val HARDHARD = DefaultVal(policiesDefaults.hardHard)
    val SUPERHARDHARD = DefaultVal(policiesDefaults.superHardHard)

    val HTTP_SUPERSIMPLE = DefaultVal(policiesDefaultsHTTP.superSimple)
    val HTTP_SIMPLE = DefaultVal(policiesDefaultsHTTP.simple)
    val HTTP_MEDIUM = DefaultVal(policiesDefaultsHTTP.medium)
    val HTTP_HARD = DefaultVal(policiesDefaultsHTTP.hard)
    val HTTP_HARDHARD = DefaultVal(policiesDefaultsHTTP.hardHard)
    val HTTP_SUPERHARDHARD = DefaultVal(policiesDefaultsHTTP.superHardHard)

    val HTTPS_SUPERSIMPLE = DefaultVal(policiesDefaultsHTTPS.superSimple)
    val HTTPS_SIMPLE = DefaultVal(policiesDefaultsHTTPS.simple)
    val HTTPS_MEDIUM = DefaultVal(policiesDefaultsHTTPS.medium)
    val HTTPS_HARD = DefaultVal(policiesDefaultsHTTPS.hard)
    val HTTPS_HARDHARD = DefaultVal(policiesDefaultsHTTPS.hardHard)
    val HTTPS_SUPERHARDHARD = DefaultVal(policiesDefaultsHTTPS.superHardHard)

    val SSH_SUPERSIMPLE = DefaultVal(policiesDefaultsSSH.superSimple)
    val SSH_SIMPLE = DefaultVal(policiesDefaultsSSH.simple)
    val SSH_MEDIUM = DefaultVal(policiesDefaultsSSH.medium)
    val SSH_HARD = DefaultVal(policiesDefaultsSSH.hard)
    val SSH_HARDHARD = DefaultVal(policiesDefaultsSSH.hardHard)
    val SSH_SUPERHARDHARD = DefaultVal(policiesDefaultsSSH.superHardHard)

    def withoutProtocol: SecurityPolicy.Default.ValueSet = this.values.filter(_.policy.transmissionProtocol.isEmpty)

    def all: Seq[SecurityPolicy] = (for default <- this.values.toSeq yield SecurityPolicy(default.name, default.description))

    def credentialsPoliciesFrom(name: String): Option[Seq[CredentialPolicy]] =
      val default = this.values.find(_.name == name)
      if default.isDefined then Some(default.get.policy.credentialPolicies) else None