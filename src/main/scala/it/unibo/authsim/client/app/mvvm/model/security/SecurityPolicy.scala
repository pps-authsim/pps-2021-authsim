package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.library.dsl.policy.defaults.PolicyDefault
import it.unibo.authsim.library.dsl.policy.model.Policy

case class SecurityPolicy(val policy: String, val description: String)

object SecurityPolicy:
  private val policiesDefaults = PolicyDefault()
  
  enum Default(val policy: Policy, val description: String):
    //TODO: add description policy
    case SUPERSIMPLE extends Default(policiesDefaults.superSimple, "")
    case SIMPLE extends Default(policiesDefaults.simple, "")
    case MEDIUM extends Default(policiesDefaults.medium, "")
    case HARD extends Default(policiesDefaults.hard, "")
    case HARDHARD extends Default(policiesDefaults.hardHard, "")
    case SUPERHARDHARD extends Default(policiesDefaults.superHardHard, "")
    def name: String = policy.name
    override def toString: String = s"${this.policy.name} - ${this.description}"