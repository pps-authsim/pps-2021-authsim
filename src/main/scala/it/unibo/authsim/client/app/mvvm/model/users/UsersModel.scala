package it.unibo.authsim.client.app.mvvm.model.users

import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import it.unibo.authsim.library.user.model.User

import scala.collection.mutable.ListBuffer
import it.unibo.authsim.client.app.mvvm.model.security.SecurityPolicy

/**
 * Simple data holder for users tab
 * @param usersList currenty available users
 * @param presetsList currently selected user
 * @param presetDescription currently avaialble generation presets
 * @param selectedPreset currently selected preset
 */
class UsersModel(var usersList: ObservableListBuffer[User] = ObservableListBuffer(),
                 var presetsList: ObservableListBuffer[SecurityPolicy] = ObservableListBuffer(),
                 var presetDescription: Option[String] = Option.empty,
                 var selectedPreset: Option[String] = Option.empty)
