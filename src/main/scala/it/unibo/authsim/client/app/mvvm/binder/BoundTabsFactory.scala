package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.view.tabs.{AttackTab, SecurityTab, UsersTab}
import it.unibo.authsim.client.app.mvvm.viewmodel.ViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.properties.AttackSequenceProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.{CredentialsSourceProperties, SecurityPoliciesProperties}
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.{AddUserFormProperties, GenerateUsersFormProperties, UsersListViewProperties}
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Tab
import scalafx.Includes.*

class BoundTabsFactory(private val viewModel: ViewModel) {

  def makeBoundUsersTab(): Tab = {
    val tabContent = new UsersTab

    val addUserFormProperties = new AddUserFormProperties(tabContent.usernameField.text, tabContent.passwordField.text);
    val generateUsersForm = new GenerateUsersFormProperties(tabContent.quantityField.text, tabContent.presetSelect.value);
    val usersListProperties = new UsersListViewProperties(tabContent.usersList.items, tabContent.usersList.selectionModel);

    val usersViewModel = new UsersViewModel(addUserFormProperties, generateUsersForm, usersListProperties)

    tabContent.saveButton.setOnAction((e: ActionEvent) => viewModel.saveUser())

    tabContent.generateButton.setOnAction((e: ActionEvent) => viewModel.generateUsers())

    tabContent.resetButton.setOnAction((e: ActionEvent) => viewModel.deleteSelectedUsers())
    tabContent.deleteSelectedButton.setOnAction((e: ActionEvent) => viewModel.resetUsers())

    viewModel.bindUsersViewModel(usersViewModel)

    makeTab("Users", tabContent)
  }

  def makeSecurityTab(): Tab = {
    val tabContent = new SecurityTab

    val securityPoliciesProperties = new SecurityPoliciesProperties(tabContent.securityPoliciesList.items, tabContent.securityPoliciesList.selectionModel, tabContent.securityPolicyDescription.text)
    val credentialsSourceProperties = new CredentialsSourceProperties(tabContent.credentialsSourceList.items, tabContent.credentialsSourceList.selectionModel, tabContent.credentialsSourceDescription.text)

    val securityViewModel = new SecurityViewModel(securityPoliciesProperties, credentialsSourceProperties)

    viewModel.bindSecurityViewModel(securityViewModel)

    makeTab("Security", tabContent)
  }

  def makeAttackTab(): Tab = {
    val tabContent = new AttackTab

    val attackSequenceProperties = new AttackSequenceProperties(
      tabContent.attackSequenceList.items,
      tabContent.attackSequenceList.selectionModel,
      tabContent.attackSequenceDescription.text,
      tabContent.attackLog.text
    )

    val attackViewModel = new AttackViewModel(attackSequenceProperties)

    tabContent.launchAttackButton.setOnAction((e: ActionEvent) => viewModel.launchAttack())

    viewModel.bindAttackViewModel(attackViewModel)

    makeTab("Attack", tabContent)
  }

  private def makeTab(title: String, tabContent: Node): Tab = {
    return new Tab {
      text = title
      closable = false
      content = tabContent
    }
  }

}
