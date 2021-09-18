package it.unibo.authsim.client.app.mvvm.viewmodel.users.properties

import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import scalafx.beans.property.ObjectProperty

case class UsersListViewProperties(
                             val usersListProperty: ObjectProperty[ObservableList[UserEntry]],
                             val usersListSelectedProperty: ReadOnlyObjectProperty[UserEntry]
                             )