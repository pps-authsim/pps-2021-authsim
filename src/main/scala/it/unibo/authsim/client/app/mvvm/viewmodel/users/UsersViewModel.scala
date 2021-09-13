package it.unibo.authsim.client.app.mvvm.viewmodel.users

import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.{AddUserFormProperties, GenerateUsersFormProperties, UsersListViewProperties}
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import javafx.beans.property.StringProperty

class UsersViewModel(
                      val addUserFormProperties: AddUserFormProperties,
                      val generateUsersFormProperties: GenerateUsersFormProperties,
                      val usersListProperties: UsersListViewProperties
                    )
