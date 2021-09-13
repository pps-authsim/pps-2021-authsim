package it.unibo.authsim.client.app.mvvm.model.security

import scala.collection.mutable.ListBuffer

class SecurityModel() {

  val securityPolicyList = new ListBuffer[SecurityPolicy]()
  val credentialsSourceList = new ListBuffer[CredentialsSource]()

}
