package it.unibo.authsim.client.app.mvvm.viewmodel.security.properties

import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

class CredentialsSourceProperties(
                                   val credentialsSourceList: ObjectProperty[ObservableList[String]],
                                   val credentialsSourceListSelectionModel: scalafx.beans.property.ObjectProperty[javafx.scene.control.MultipleSelectionModel[String]],
                                   val credentialsSourceDescription: StringProperty
                                 ) {

}
