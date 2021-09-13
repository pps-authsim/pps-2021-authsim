package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.view.AuthsimView
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.{AttackSequenceEntry, AttackTab}
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.properties.AttackSequenceProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.{CredentialsSourceProperties, SecurityPoliciesProperties}
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.{AddUserFormProperties, GenerateUsersFormProperties, UsersListViewProperties}
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry, SecurityTab}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.ListChangeListener
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Tab
import scalafx.Includes.*

class ViewModelBinder(private val view: AuthsimView, private val viewModel: AuthsimViewModel) {

  def bind(): Unit = {
    bindUsersTab(view.usersTab)
    bindSecurityTab(view.securityTab)
    bindAttackTab(view.attackTab)
  }

  private def bindUsersTab(tab: UsersTab): Unit = {

    val addUserFormProperties = new AddUserFormProperties(tab.usernameField.text, tab.passwordField.text);
    val generateUsersForm = new GenerateUsersFormProperties(tab.quantityField.text, tab.presetSelect.value);
    val usersListProperties = new UsersListViewProperties(tab.usersList.items, tab.usersList.selectionModel);

    val usersViewModel = new UsersViewModel(addUserFormProperties, generateUsersForm, usersListProperties)

    tab.saveButton.setOnAction((e: ActionEvent) => viewModel.saveUser())

    tab.generateButton.setOnAction((e: ActionEvent) => viewModel.generateUsers())

    tab.deleteSelectedButton.setOnAction((e: ActionEvent) => viewModel.deleteSelectedUsers())
    tab.resetButton.setOnAction((e: ActionEvent) => viewModel.resetUsers())

    viewModel.bindUsersViewModel(usersViewModel)
  }

  private def bindSecurityTab(tab: SecurityTab): Unit = {
    val securityPoliciesProperties = new SecurityPoliciesProperties(tab.securityPoliciesList.items, tab.securityPoliciesList.selectionModel, tab.securityPolicyDescription.text)

    val credentialsSourceProperties = new CredentialsSourceProperties(tab.credentialsSourceList.items, tab.credentialsSourceList.selectionModel, tab.credentialsSourceDescription.text)

    val securityViewModel = new SecurityViewModel(securityPoliciesProperties, credentialsSourceProperties)

    tab.securityPoliciesList.selectionModel.value.selectedItemProperty().addListener(new ChangeListener[SecurityPolicyEntry] {
      def changed(o: ObservableValue[_ <: SecurityPolicyEntry], oldValue: SecurityPolicyEntry, newValue: SecurityPolicyEntry): Unit = {
        tab.securityPolicyDescription.text = newValue.description
      }
    })

    tab.credentialsSourceList.selectionModel.value.selectedItemProperty().addListener(new ChangeListener[CredentialsSourceEntry] {
      def changed(o: ObservableValue[_ <: CredentialsSourceEntry], oldValue: CredentialsSourceEntry, newValue: CredentialsSourceEntry): Unit = {
        tab.credentialsSourceDescription.text = newValue.description
      }
    })

    viewModel.bindSecurityViewModel(securityViewModel)
  }

  private def bindAttackTab(tab: AttackTab): Unit = {
    val attackSequenceProperties = new AttackSequenceProperties(
      tab.attackSequenceList.items,
      tab.attackSequenceList.selectionModel,
      tab.attackSequenceDescription.text,
      tab.attackLog.text
    )

    val attackViewModel = new AttackViewModel(attackSequenceProperties)

    tab.launchAttackButton.setOnAction((e: ActionEvent) => viewModel.launchAttack())

    tab.attackSequenceList.selectionModel.value.selectedItemProperty().addListener(new ChangeListener[AttackSequenceEntry] {
      def changed(o: ObservableValue[_ <: AttackSequenceEntry], oldValue: AttackSequenceEntry, newValue: AttackSequenceEntry): Unit = {
        tab.attackSequenceDescription.text = newValue.description
      }
    })

    viewModel.bindAttackViewModel(attackViewModel)
  }

}
