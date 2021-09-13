package it.unibo.authsim.client.app.mvvm.model.security

import scala.collection.mutable.ListBuffer

class SecurityModel(
                     val securityPolicyList: ListBuffer[SecurityPolicy] = new ListBuffer(),
                     val credentialsSourceList: ListBuffer[CredentialsSource] = new ListBuffer()
                   )
