package it.unibo.authsim.client.app.mvvm.viewmodel.security.properties

import it.unibo.authsim.client.app.mvvm.view.tabs.security.SecurityPolicyEntry
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

case class SecurityPoliciesProperties(
                                  val securityPoliciesList: ObjectProperty[ObservableList[SecurityPolicyEntry]],
                                  val securityPoliciesListSelectedValue: ReadOnlyObjectProperty[SecurityPolicyEntry],
                                  val securityPoliciesDescription: StringProperty
                                )
