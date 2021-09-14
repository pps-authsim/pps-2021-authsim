package it.unibo.authsim.client.app.mvvm.viewmodel.users

import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.{AddUserFormProperties, GenerateUsersFormProperties, UsersListViewProperties}
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import javafx.beans.property.StringProperty
import scalafx.scene.control.{TextField, TextFormatter}
import scalafx.scene.control.TextFormatter.Change

object UsersViewModel:
  def makeNumberTextField(): TextField = new TextField {
    val numberFilter: Change => Change = (change: Change) => {
      val text = change.text

      if (text.matches("[0-9]*")) then
        change
      else
        null
    }
    textFormatter = new TextFormatter(numberFilter)
  }

class UsersViewModel(
                      val addUserFormProperties: AddUserFormProperties,
                      val generateUsersFormProperties: GenerateUsersFormProperties,
                      val usersListProperties: UsersListViewProperties
                    )
