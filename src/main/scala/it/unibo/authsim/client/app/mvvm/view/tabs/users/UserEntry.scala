package it.unibo.authsim.client.app.mvvm.view.tabs.users

class UserEntry(username: String, password: String) {
  override def toString: String = username + " " + password
}
