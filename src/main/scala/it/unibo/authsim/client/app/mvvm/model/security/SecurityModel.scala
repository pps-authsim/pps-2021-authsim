package it.unibo.authsim.client.app.mvvm.model.security

import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer

import scala.collection.mutable.ListBuffer

class SecurityModel(
                     var securityPolicyList: ObservableListBuffer[SecurityPolicy] = new ObservableListBuffer(),
                     var credentialsSourceList: ObservableListBuffer[CredentialsSource] = new ObservableListBuffer()
                   )
