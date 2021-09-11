package it.unibo.authsim.client.app.mvvm.viewmodel

import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel

class ViewModel() {

  var usersViewModel: UsersViewModel = null
  var securityViewModel: SecurityViewModel = null
  var attackViewModel: AttackViewModel = null

  def bindUsersViewModel(usersViewModel: UsersViewModel): Unit = {
    this.usersViewModel = usersViewModel
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

    println(username + password)
  }

  def generateUsers(): Unit = {
    val quantity = usersViewModel.generateUsersFormProperties.quantityProperty.getValue();
    val preset = usersViewModel.generateUsersFormProperties.presetProperty.getValue();

    println(quantity + preset) // TODO add business logic
  }

  def deleteSelectedUsers():Unit = {
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
    println(selectedProcedure)
  }

}

