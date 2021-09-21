package it.unibo.authsim.client.app.mvvm.viewmodel.attack.properties

import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackSequenceEntry
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

case class AttackSequenceProperties(
                                val attackSequenceList: ObjectProperty[ObservableList[AttackSequenceEntry]],
                                val attackSequenceListSelectedValue: ReadOnlyObjectProperty[AttackSequenceEntry],
                                val attackSequenceDescription: StringProperty,
                                val attackLog: StringProperty
                              )
