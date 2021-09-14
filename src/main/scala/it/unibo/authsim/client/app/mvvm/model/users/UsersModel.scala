package it.unibo.authsim.client.app.mvvm.model.users

import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer

import scala.collection.mutable.ListBuffer

class UsersModel(var usersList: ObservableListBuffer[User] = ObservableListBuffer())
