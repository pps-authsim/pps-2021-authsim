package it.unibo.authsim.client.app.mvvm.viewmodel.users.properties

import javafx.collections.ObservableList
import scalafx.beans.property.{ObjectProperty}

class UsersListViewProperties(
                             val usersListProperty: ObjectProperty[ObservableList[String]],
                             val usersListSelectionModel: scalafx.beans.property.ObjectProperty[javafx.scene.control.MultipleSelectionModel[String]]
                             )