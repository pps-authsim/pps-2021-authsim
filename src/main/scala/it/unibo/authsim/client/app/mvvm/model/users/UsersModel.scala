package it.unibo.authsim.client.app.mvvm.model.users

import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import it.unibo.authsim.library.user.model.User

import scala.collection.mutable.ListBuffer
import it.unibo.authsim.client.app.mvvm.model.security.SecurityPolicy

class UsersModel(var usersList: ObservableListBuffer[User] = ObservableListBuffer(),
                 var presetsList: ObservableListBuffer[SecurityPolicy] = ObservableListBuffer(),
                 var presetDescription: Option[String] = Option.empty,
                 var selectedPreset: Option[String] = Option.empty)
