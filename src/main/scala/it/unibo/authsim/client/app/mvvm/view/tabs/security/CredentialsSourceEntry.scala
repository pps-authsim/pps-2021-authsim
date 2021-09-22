package it.unibo.authsim.client.app.mvvm.view.tabs.security

case class CredentialsSourceEntry(val policy: String, val description: String):
  override def toString(): String = policy

