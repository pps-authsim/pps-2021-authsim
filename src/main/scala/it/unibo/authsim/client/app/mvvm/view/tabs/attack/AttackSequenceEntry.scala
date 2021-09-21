package it.unibo.authsim.client.app.mvvm.view.tabs.attack

case class AttackSequenceEntry(sequence: String, description: String):
  override def toString: String = sequence
