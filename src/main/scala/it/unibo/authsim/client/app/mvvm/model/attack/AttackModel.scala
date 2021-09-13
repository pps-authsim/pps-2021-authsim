package it.unibo.authsim.client.app.mvvm.model.attack

import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackSequenceEntry

import scala.collection.mutable.ListBuffer

class AttackModel {

  val securityPolicyList = new ListBuffer[AttackSequenceEntry]()
  
}
