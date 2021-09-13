package it.unibo.authsim.client.app.mvvm.viewmodel.attack.properties

import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackSequenceEntry
import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

class AttackSequenceProperties(
                                val attackSequenceList: ObjectProperty[ObservableList[AttackSequenceEntry]],
                                val attackSequenceListSelectionModel: scalafx.beans.property.ObjectProperty[javafx.scene.control.MultipleSelectionModel[AttackSequenceEntry]],
                                val attackSequenceDescription: StringProperty,
                                val attackLog: StringProperty
                              ) {

}
