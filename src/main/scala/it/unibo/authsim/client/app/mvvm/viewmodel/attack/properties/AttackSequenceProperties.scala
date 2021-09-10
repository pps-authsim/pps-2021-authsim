package it.unibo.authsim.client.app.mvvm.viewmodel.attack.properties

import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

class AttackSequenceProperties(
                                val attackSequenceList: ObjectProperty[ObservableList[String]],
                                val attackSequenceListSelectionModel: scalafx.beans.property.ObjectProperty[javafx.scene.control.MultipleSelectionModel[String]],
                                val attackSequenceDescription: StringProperty,
                                val attackLog: StringProperty
                              ) {

}
