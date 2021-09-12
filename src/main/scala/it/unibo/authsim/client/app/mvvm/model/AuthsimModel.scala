package it.unibo.authsim.client.app.mvvm.model

import it.unibo.authsim.client.app.mvvm.model.attack.AttackModel
import it.unibo.authsim.client.app.mvvm.model.security.SecurityModel
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel

class AuthsimModel(val usersModel: UsersModel, val securityModel: SecurityModel, val attackModel: AttackModel) {

}
