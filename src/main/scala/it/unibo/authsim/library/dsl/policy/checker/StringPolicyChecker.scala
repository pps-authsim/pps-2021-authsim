package it.unibo.authsim.library.dsl.policy.checker

import it.unibo.authsim.library.dsl.policy.model.StringPolicies.StringPolicy

object StringPolicyChecker:

  def apply(policy: StringPolicy): PolicyChecker[String] = new PolicyChecker[String]:
    override def check(value: String): Boolean = policy.patterns forall { _.matches(value.trim) }