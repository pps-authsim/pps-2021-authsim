package it.unibo.authsim.client.app.mvvm.model.attack

import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence

import scala.collection.mutable.ListBuffer

class AttackModel(val securityPolicyList: ListBuffer[AttackSequence] = new ListBuffer())
