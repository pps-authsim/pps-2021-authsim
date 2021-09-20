package it.unibo.authsim.client.app.mvvm.model

import it.unibo.authsim.client.app.mvvm.model.attack.AttackModel
import it.unibo.authsim.client.app.mvvm.model.security.SecurityModel
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import javafx.collections.ObservableList

/**
 * Application's model
 * @param usersModel users model
 * @param securityModel security model
 * @param attackModel attack model
 */
class AuthsimModel(val usersModel: UsersModel, val securityModel: SecurityModel, val attackModel: AttackModel)
