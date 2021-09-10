package it.unibo.authsim.client.app.mvvm.viewmodel.users.properties

import scalafx.beans.property.{StringProperty, ObjectProperty}

class GenerateUsersFormProperties(
                                   val quantityProperty: StringProperty,
                                   val presetProperty: ObjectProperty[String]
                                 )
