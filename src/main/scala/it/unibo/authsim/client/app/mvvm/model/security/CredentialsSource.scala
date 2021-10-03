package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType

case class CredentialsSource(val source: CredentialsSourceType, val description: String)
