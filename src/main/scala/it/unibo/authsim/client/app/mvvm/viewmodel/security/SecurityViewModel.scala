package it.unibo.authsim.client.app.mvvm.viewmodel.security

import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.{CredentialsSourceProperties, SecurityPoliciesProperties}
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties

/**
 * Security Properties Container
 * @param securityPoliciesProperties security policies properties
 * @param credentialsSourceProperties credentials source properties
 */
class SecurityViewModel(
                         val securityPoliciesProperties: SecurityPoliciesProperties,
                         val credentialsSourceProperties: CredentialsSourceProperties
                       )
