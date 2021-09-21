package it.unibo.authsim.client.app.mvvm.view.tabs.users

case class UserEntry(val username: String, val password: String) {
  override def toString: String = username + " " + password
}
