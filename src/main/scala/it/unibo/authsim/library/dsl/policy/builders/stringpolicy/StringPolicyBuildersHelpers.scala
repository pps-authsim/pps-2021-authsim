package it.unibo.authsim.library.dsl.policy.builders.stringpolicy

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{MoreRestrictStringPolicy, RestrictStringPolicy, StringPolicy}

object StringPolicyBuildersHelpers:
  def buildToString(name: String, stringPolicy: StringPolicy | RestrictStringPolicy | MoreRestrictStringPolicy): String =
    val string: StringBuilder = new StringBuilder(name).append(" { ")

    if stringPolicy.isInstanceOf[RestrictStringPolicy] then
      val policyRestrict = stringPolicy.asInstanceOf[RestrictStringPolicy]
      string
        .append("minimum length = ").append(policyRestrict.minimumLength).append(", ")
        .append("maximum length = ").append(policyRestrict.maximumLength)

    if stringPolicy.isInstanceOf[MoreRestrictStringPolicy] then
      val policyMoreRestrict: MoreRestrictStringPolicy = stringPolicy.asInstanceOf[MoreRestrictStringPolicy]
      string.append(", ").append("minimum uppercase chars = ").append(policyMoreRestrict.minimumUpperChars)
      string.append(", ").append("minimum lowercase chars = ").append(policyMoreRestrict.minimumLowerChars)
      string.append(", ").append("minimum symbols = ").append(policyMoreRestrict.minimumSymbols)
      string.append(", ").append("minimum numbers = ").append(policyMoreRestrict.minimumNumbers)

    if stringPolicy.isInstanceOf[StringPolicy] then
      val policyString = stringPolicy.asInstanceOf[StringPolicy]
      string.append(", ")
        .append("patterns = ").append(policyString.patterns).append(", ")
        .append("alphabet = ").append(policyString.alphabet)

    string.append(" } ").toString
