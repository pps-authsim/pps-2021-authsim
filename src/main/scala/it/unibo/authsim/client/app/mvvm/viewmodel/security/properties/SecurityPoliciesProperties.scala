package it.unibo.authsim.client.app.mvvm.viewmodel.security.properties

import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

class SecurityPoliciesProperties(
                                  val securityPoliciesList: ObjectProperty[ObservableList[String]],
                                  val securityPoliciesListSelectionModel: scalafx.beans.property.ObjectProperty[javafx.scene.control.MultipleSelectionModel[String]],
                                  val securityPoliciesDescription: StringProperty
                                ) {

}
