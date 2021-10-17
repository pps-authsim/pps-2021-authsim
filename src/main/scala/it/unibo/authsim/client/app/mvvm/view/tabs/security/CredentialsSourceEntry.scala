package it.unibo.authsim.client.app.mvvm.view.tabs.security

import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType

case class CredentialsSourceEntry(val source: CredentialsSourceType, val description: String):
  override def toString(): String = source.toString

