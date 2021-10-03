package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer

import scala.collection.mutable.ListBuffer

class SecurityModel(
                     var securityPolicyList: ObservableListBuffer[SecurityPolicy] = new ObservableListBuffer(),
                     var selectedSecurityPolicy: Option[SecurityPolicy] = Option.empty,
                     var credentialsSourceList: ObservableListBuffer[CredentialsSource] = new ObservableListBuffer(),
                     var selectedCredentialsSource: Option[CredentialsSource] = Option.empty
                   )
