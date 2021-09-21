package it.unibo.authsim.client.app.mvvm.viewmodel.users.properties

import scalafx.beans.property.{StringProperty, ObjectProperty}

case class GenerateUsersFormProperties(
                                   val quantityProperty: StringProperty,
                                   val presetProperty: ObjectProperty[String]
                                 )
