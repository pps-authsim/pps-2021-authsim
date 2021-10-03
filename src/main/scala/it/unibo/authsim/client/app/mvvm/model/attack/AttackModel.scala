package it.unibo.authsim.client.app.mvvm.model.attack

import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence
import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer

import scala.collection.mutable.ListBuffer

class AttackModel(
                   var attackSequenceList: ObservableListBuffer[AttackSequence] = new ObservableListBuffer(),
                   var selectedAttackSequence: Option[AttackSequence] = Option.empty
                 )
