package it.unibo.authsim.client.app.mvvm.viewmodel.users

import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.{AddUserFormProperties, GenerateUsersFormProperties, UsersListViewProperties}
import javafx.beans.property.StringProperty

class UsersViewModel(
                      val addUserFormProperties: AddUserFormProperties,
                      val generateUsersFormProperties: GenerateUsersFormProperties,
                      val usersListProperties: UsersListViewProperties
                    ) {



}
