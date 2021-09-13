package it.unibo.authsim.client.app.mvvm.viewmodel.security

import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.{CredentialsSourceProperties, SecurityPoliciesProperties}
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties

class SecurityViewModel(
                         val securityPoliciesProperties: SecurityPoliciesProperties,
                         val credentialsSourceProperties: CredentialsSourceProperties
                       )
