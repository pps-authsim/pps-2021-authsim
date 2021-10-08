package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.client.app.mvvm.model.security
import it.unibo.authsim.library.dsl.Protocol
import it.unibo.authsim.library.dsl.Protocol.*
import it.unibo.authsim.library.dsl.policy.defaults.PolicyDefault
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, PasswordPolicy, UserIDPolicy, OTPPolicy}

case class SecurityPolicy(val policy: String, val description: String)

object SecurityPolicy:

  /**
   * ''Default'' is a rappresentation of default policy
   */
  object Default extends Enumeration:
    type Default = Value

    implicit class RichString(base: String):
      def descriptor(defaultVal: DefaultVal) =
        val alphabets = defaultVal.policy.credentialPolicies.map {
          case u: UserIDPolicy => "userID" -> u.alphabet.alphanumericsymbols.mkString
          case p: PasswordPolicy => "password" -> p.alphabet.alphanumericsymbols.mkString
          case o: OTPPolicy => "otp" -> o.alphabet.alphanumericsymbols.mkString
        }.reverse.toMap

        s"""${base}\n
            ${if defaultVal.policy.transmissionProtocol.isDefined then s"Credentials are trasmitted with protocol ${defaultVal.policy.transmissionProtocol.get.getClass.getSimpleName.toUpperCase}." else "No protocol." }\n
            The alphabet:\n${alphabets.map((who, alpha) => s"\t\t- $who : $alpha\n").mkString}
        """

    protected case class DefaultVal(val policy: Policy) extends super.Val:
      def name: String = (if policy.transmissionProtocol.isDefined then s"${policy.transmissionProtocol.get.getClass.getSimpleName}-" else "") + policy.name
      def description: String = this.policy.name match
        case "SuperSimple" =>
            """
             An userID has
              - minimum length of 3 characters
              - maximum length of 6 characters

             A password has
              - minimum length of 3 characters
              - maximum length of 7 characters

            Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)
        case "Simple" =>
            """
             An userID has
              - minimum length of 8 characters

             A password has
              - minimum length of 3 characters
              - maximum length of 7 characters

             Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)
        case "Medium" =>
            """
             An userID has
              - minimum length of 8 characters
              - a minimum of 1 symbols

             A password has
              - minimum length of 8 characters and

             Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)
        case "Hard" =>
            """
             An userID has
              - minimum length of 10 characters
              - a minimum of 2 symbols
              - a minimum of 3 uppercase characters

             A password has
              - minimum length of 8 characters
              - a minimum of 1 symbols

             Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)
        case "SuperHard" =>
            """
             An userID has
              - minimum length of 10 characters
              - a minimum of 2 symbols
              - a minimum of 3 uppercase characters

             A password has
              - minimum length of 8 characters
              - a minimum of 1 symbols
              - a minimum of 3 uppercase characters

            The userID is stored in plain text and password is stored with SHA256 algoritm using salt value with
              - minimum length of 8 characters
              - a minimum of 1 symbols
              - a minimum of 1 uppercase characters
            in the database.""".descriptor(this)
        case "SuperHardHard" =>
            """
               An userID has
                - minimum length of 10 characters
                - a minimum of 2 symbols
                - a minimum of 3 uppercase characters

               A password has
                - minimum length of 10 characters
                - a minimum of 2 symbols
                - a minimum of 3 uppercase characters

              The userID is stored in plain text and password is stored with SHA384 algoritm using salt value with
                - minimum length of 10 characters
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

    /**
     * @return a sequence of defined default policies that do not have a transmission protocol
     */
    def withoutProtocol: Seq[Default.Value] = this.values.toSeq.filter(_.policy.transmissionProtocol.isEmpty)

    /**
     * @return a sequence of all defined default policies mapped in [[SecurityPolicy]]
     */
    def all: Seq[SecurityPolicy] = (for default <- this.values.toSeq yield SecurityPolicy(default.name, default.description))

    /**
     * @param name name of selected default policy
     * @return a optional sequence of [[CredentialPolicy credential policies]] of selected default policy
     */
    def credentialsPoliciesFrom(name: String): Option[Seq[CredentialPolicy]] =
      val default = this.values.find(_.name == name)
      if default.isDefined then Some(default.get.policy.credentialPolicies) else None