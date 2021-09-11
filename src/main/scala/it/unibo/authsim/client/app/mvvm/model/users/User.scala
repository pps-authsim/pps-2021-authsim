package it.unibo.authsim.client.app.mvvm.model.users

class User(username: String, password: String) {
  override def toString: String = username + " " + password
}
