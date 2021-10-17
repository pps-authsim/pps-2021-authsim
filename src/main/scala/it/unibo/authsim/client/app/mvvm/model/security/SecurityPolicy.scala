package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.library.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.policy.defaults.PolicyDefault
import it.unibo.authsim.library.policy.model.Policy
import it.unibo.authsim.library.policy.model.StringPolicies.{CredentialPolicy, OTPPolicy, PasswordPolicy, UserIDPolicy}

case class SecurityPolicy(val policy: String, val description: String)

object SecurityPolicy:

  /**
   * ''Default'' is a rappresentation of default policy
   */
  object Default extends Enumeration:
    type Default = Value

    implicit class RichString(base: String):
      def descriptor(defaultVal: DefaultVal)(showAlphabet: Boolean) =
        val alphabets = defaultVal.policy.credentialPolicies.map {
          case u: UserIDPolicy => "userID" -> u.alphabet.alphanumericsymbols.mkString
          case p: PasswordPolicy => "password" -> p.alphabet.alphanumericsymbols.mkString
          case o: OTPPolicy => "otp" -> o.alphabet.digits.mkString
        }.toMap

        s"""${base}\n
            ${if defaultVal.policy.transmissionProtocol.isDefined then s"Credentials are trasmitted with protocol ${defaultVal.policy.transmissionProtocol.get.getClass.getSimpleName.toUpperCase}." else "No protocol." }\n
            ${if showAlphabet then s"The alphabet:\n${alphabets.map((who, alpha) => s"\t\t- $who : $alpha\n").mkString}" else ""}
        """

    protected case class DefaultVal(val policy: Policy) extends super.Val:
      def name: String = (if policy.transmissionProtocol.isDefined then s"${policy.transmissionProtocol.get.getClass.getSimpleName}-" else "") + policy.name
      def description(showAlphabet: Boolean): String = this.policy.name match
        case "SuperSimple" =>
            """
             An userID has
              - minimum length of 3 characters
              - maximum length of 6 characters

             A password has
              - minimum length of 3 characters
              - maximum length of 7 characters

            Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)(showAlphabet)
        case "Simple" =>
            """
             An userID has
              - minimum length of 8 characters

             A password has
              - minimum length of 3 characters
              - maximum length of 7 characters

             Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)(showAlphabet)
        case "Medium" =>
            """
             An userID has
              - minimum length of 8 characters
              - a minimum of 1 symbols

             A password has
              - minimum length of 8 characters and

             Credentials (userID, password) are stored in plain text in the database.""".descriptor(this)(showAlphabet)
        case "Hard" =>
            """
             An userID has
              - minimum length of 10 characters
              - a minimum of 2 symbols
              - a minimum of 3 uppercase characters

             A password has
              - minimum length of 8 characters
              - a minimum of 1 symbols

             The userID is stored in plain text and password is stored with SHA1.""".descriptor(this)(showAlphabet)
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
            in the database.""".descriptor(this)(showAlphabet)
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
              in the database.""".descriptor(this)(showAlphabet)

      override def compare(that: SecurityPolicy.Default.Value): Int = this.name compare that.name
      override def toString: String = s"${policy.name} - ${description}"

    import scala.language.implicitConversions
    implicit def valueToDefaultVal(x: Value): DefaultVal = x.asInstanceOf[DefaultVal]

    private val policiesDefaults = PolicyDefault()

    val SUPERSIMPLE = DefaultVal(policiesDefaults.superSimple)
    val SIMPLE = DefaultVal(policiesDefaults.simple)
    val MEDIUM = DefaultVal(policiesDefaults.medium)
    val HARD = DefaultVal(policiesDefaults.hard)
    val HARDHARD = DefaultVal(policiesDefaults.hardHard)
    val SUPERHARDHARD = DefaultVal(policiesDefaults.superHardHard)

    /**
     * @return a sequence of defined default policies that do not have a transmission protocol mapped in [[SecurityPolicy]]
     */
    def withoutProtocol: Seq[SecurityPolicy] = for default <- this.values.toSeq.filter(_.policy.transmissionProtocol.isEmpty) yield SecurityPolicy(default.name, default.description(showAlphabet = true))

    /**
     * @return a sequence of all defined default policies mapped in [[SecurityPolicy]]
     */
    def all: Seq[SecurityPolicy] = for default <- this.values.toSeq yield SecurityPolicy(default.name, default.description(showAlphabet = false))

    /**
     * @param name name of selected default policy
     * @return a optional sequence of [[CredentialPolicy credential policies]] of selected default policy
     */
    def credentialsPoliciesFrom(name: String): Option[Seq[CredentialPolicy]] =
      this.values.find(_.name == name) match
        case Some(default) => Some(default.policy.credentialPolicies)
        case _ => None


    /**
     * @param name name of selected default policy
     * @return a optional [[CryptographicAlgorithm cryptographic algorithm]] of selected default policy
     */
    def cryptographicAlgorithmFrom(name: String): Option[CryptographicAlgorithm] =
      this.values.find(_.name == name) match
        case Some(default) => default.policy.cryptographicAlgorithm
        case _ => None