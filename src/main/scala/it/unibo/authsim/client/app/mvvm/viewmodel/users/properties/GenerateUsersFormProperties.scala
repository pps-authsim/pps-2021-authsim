package it.unibo.authsim.client.app.mvvm.viewmodel.users.properties

import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserGenerationPreset
import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

case class GenerateUsersFormProperties(
                                        val quantityProperty: StringProperty,
                                        val presetListProperty: ObjectProperty[ObservableList[UserGenerationPreset]],
                                        val presetProperty: ObjectProperty[UserGenerationPreset]
                                 )
