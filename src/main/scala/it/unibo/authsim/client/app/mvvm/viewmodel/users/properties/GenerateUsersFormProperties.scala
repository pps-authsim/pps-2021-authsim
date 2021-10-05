package it.unibo.authsim.client.app.mvvm.viewmodel.users.properties

import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty, StringProperty}

case class GenerateUsersFormProperties(
                                   val quantityProperty: StringProperty,
                                   val presetListProperty: ObjectProperty[ObservableList[String]],
                                   val presetProperty: ObjectProperty[String]
                                 )
