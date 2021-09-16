package it.unibo.authsim.client.app.mvvm.view.tabs.security

case class SecurityPolicyEntry(val policy: String, val description: String):
  override def toString: String = policy
