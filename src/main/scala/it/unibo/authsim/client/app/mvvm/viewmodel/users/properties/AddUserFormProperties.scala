package it.unibo.authsim.client.app.mvvm.viewmodel.users.properties

import scalafx.beans.property.StringProperty

case class AddUserFormProperties(
                             val usernameProperty: StringProperty,
                             val passwordProperty: StringProperty
                           )
