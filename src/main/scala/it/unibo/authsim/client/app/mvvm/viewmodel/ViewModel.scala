package it.unibo.authsim.client.app.mvvm.viewmodel

import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimView
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.users.User
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import javafx.collections.ObservableList
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer

class ViewModel(
                 private val model: AuthsimModel
               ) {

  var usersViewModel: UsersViewModel = null
  var securityViewModel: SecurityViewModel = null
  var attackViewModel: AttackViewModel = null

  def bindUsersViewModel(usersViewModel: UsersViewModel): Unit = {
    this.usersViewModel = usersViewModel
    val usersModel = model.usersModel;

    usersViewModel.usersListProperties.usersListProperty.value.addAll(new UserEntry("user", "pass"))
  }

  def bindSecurityViewModel(securityViewModel: SecurityViewModel): Unit = {
    this.securityViewModel = securityViewModel
  }

  def bindAttackViewModel(attackViewModel: AttackViewModel): Unit = {
    this.attackViewModel = attackViewModel
  }

  def saveUser(): Unit = {
    val username = usersViewModel.addUserFormProperties.usernameProperty.getValue()
    val password = usersViewModel.addUserFormProperties.passwordProperty.getValue()

    val user = new User(username, password)
    val userEntry = new UserEntry(username, password)

    // TODO make observable list?
    model.usersModel.usersList += user
    usersViewModel.usersListProperties.usersListProperty.value += userEntry
  }

  def generateUsers(): Unit = {
    val quantity = usersViewModel.generateUsersFormProperties.quantityProperty.getValue();
    val preset = usersViewModel.generateUsersFormProperties.presetProperty.getValue();

    println(quantity + preset) // TODO add business logic
  }

  def deleteSelectedUsers(): Unit = {
    val users = usersViewModel.usersListProperties.usersListProperty.value
    println(users) // TODO add business logic
  }

  def resetUsers(): Unit = {
    val selectedUser = usersViewModel.usersListProperties.usersListSelectionModel.value.getSelectedItem
    println(selectedUser) // TODO add business logic
  }

  def launchAttack(): Unit = {
    val users = usersViewModel.usersListProperties.usersListProperty.value
    val selectedProcedure = attackViewModel.attackSequenceProperties.attackSequenceListSelectionModel.value.getSelectedItem
    println(selectedProcedure) // TODO add business logic
  }

}

