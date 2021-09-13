package it.unibo.authsim.client.app.mvvm.viewmodel.security.properties

import it.unibo.authsim.client.app.mvvm.view.tabs.security.CredentialsSourceEntry
import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

class CredentialsSourceProperties(
                                   val credentialsSourceList: ObjectProperty[ObservableList[CredentialsSourceEntry]],
                                   val credentialsSourceListSelectionModel: scalafx.beans.property.ObjectProperty[javafx.scene.control.MultipleSelectionModel[CredentialsSourceEntry]],
                                   val credentialsSourceDescription: StringProperty
                                 ) {

}
