package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.view.tabs.SecurityTab
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import it.unibo.authsim.client.app.mvvm.view.tabs.{AttackTab, SecurityTab}
import it.unibo.authsim.client.app.mvvm.viewmodel.ViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.properties.AttackSequenceProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.{CredentialsSourceProperties, SecurityPoliciesProperties}
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.{AddUserFormProperties, GenerateUsersFormProperties, UsersListViewProperties}
import it.unibo.authsim.client.app.mvvm.view.tabs.SecurityTab
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Tab
import scalafx.Includes.*

class TabsBinder(private val viewModel: ViewModel) {

  def bindUsersTab(tab: UsersTab): Unit = {

    val addUserFormProperties = new AddUserFormProperties(tab.usernameField.text, tab.passwordField.text);
    val generateUsersForm = new GenerateUsersFormProperties(tab.quantityField.text, tab.presetSelect.value);
    val usersListProperties = new UsersListViewProperties(tab.usersList.items, tab.usersList.selectionModel);

    val usersViewModel = new UsersViewModel(addUserFormProperties, generateUsersForm, usersListProperties)

    tab.saveButton.setOnAction((e: ActionEvent) => viewModel.saveUser())

    tab.generateButton.setOnAction((e: ActionEvent) => viewModel.generateUsers())

    tab.resetButton.setOnAction((e: ActionEvent) => viewModel.deleteSelectedUsers())
    tab.deleteSelectedButton.setOnAction((e: ActionEvent) => viewModel.resetUsers())

    viewModel.bindUsersViewModel(usersViewModel)
  }

  def bindSecurityTab(tab: SecurityTab): Unit = {
    val securityPoliciesProperties = new SecurityPoliciesProperties(tab.securityPoliciesList.items, tab.securityPoliciesList.selectionModel, tab.securityPolicyDescription.text)
    val credentialsSourceProperties = new CredentialsSourceProperties(tab.credentialsSourceList.items, tab.credentialsSourceList.selectionModel, tab.credentialsSourceDescription.text)

    val securityViewModel = new SecurityViewModel(securityPoliciesProperties, credentialsSourceProperties)

    viewModel.bindSecurityViewModel(securityViewModel)
  }

  def bindAttackTab(tab: AttackTab): Unit = {
    val attackSequenceProperties = new AttackSequenceProperties(
      tab.attackSequenceList.items,
      tab.attackSequenceList.selectionModel,
      tab.attackSequenceDescription.text,
      tab.attackLog.text
    )

    val attackViewModel = new AttackViewModel(attackSequenceProperties)

    tab.launchAttackButton.setOnAction((e: ActionEvent) => viewModel.launchAttack())

    viewModel.bindAttackViewModel(attackViewModel)
  }
  
}
