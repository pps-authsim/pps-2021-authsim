package it.unibo.authsim.client.app.mvvm.viewmodel.security.properties

import it.unibo.authsim.client.app.mvvm.view.tabs.security.SecurityPolicyEntry
import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

class SecurityPoliciesProperties(
                                  val securityPoliciesList: ObjectProperty[ObservableList[SecurityPolicyEntry]],
                                  val securityPoliciesListSelectionModel: scalafx.beans.property.ObjectProperty[javafx.scene.control.MultipleSelectionModel[SecurityPolicyEntry]],
                                  val securityPoliciesDescription: StringProperty
                                )
