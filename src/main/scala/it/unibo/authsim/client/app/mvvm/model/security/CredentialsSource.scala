package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType

/**
 * Represents credentials persistence source and its data
 * @param source credentials source provider type
 * @param description credentials source provider
 */
case class CredentialsSource(val source: CredentialsSourceType, val description: String)
