package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.view.AuthsimViewSFX
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.{AttackSequenceEntry, AttackTab}
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModelSFX
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.properties.AttackSequenceProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.{CredentialsSourceProperties, SecurityPoliciesProperties}
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.{AddUserFormProperties, GenerateUsersFormProperties, UsersListViewProperties}
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry, SecurityTab}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import it.unibo.authsim.client.app.mvvm.viewmodel.proxy.AuthsimViewModelDeferedProxy
import it.unibo.authsim.client.app.mvvm.viewmodel.security.properties.SecurityPoliciesProperties
import it.unibo.authsim.client.app.mvvm.viewmodel.users.properties.GenerateUsersFormProperties
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.ListChangeListener
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Tab
import scalafx.Includes.*

/**
 * Helper object for binding view properties to the provided viewmodel
 */
object ViewPropertiesBinder:

  def bindUsersTab(view: AuthsimViewSFX, viewModel: AuthsimViewModelDeferedProxy): UsersViewModel =
    val tab = view.usersTab

    val addUserFormProperties = new AddUserFormProperties(tab.usernameProperty, tab.passwordProperty);
    val generateUsersForm = new GenerateUsersFormProperties(tab.quantityProperty, tab.presetListProperty, tab.presetProperty, tab.presetDescriptionProperty);
    val usersListProperties = new UsersListViewProperties(tab.usersListProperty, tab.usersListSelectedProperty);

    val usersViewModel = new UsersViewModel(addUserFormProperties, generateUsersForm, usersListProperties)

    tab.bindOnSave((e: ActionEvent) => viewModel.saveUser())

    tab.bindOnGenerate((e: ActionEvent) => viewModel.generateUsers())

    tab.bindOnDeleteSelected((e: ActionEvent) => viewModel.deleteSelectedUsers())
    tab.bindOnReset((e: ActionEvent) => viewModel.resetUsers())
    
    
    usersViewModel


  def bindSecurityTab(view: AuthsimViewSFX, viewModel: AuthsimViewModelDeferedProxy): SecurityViewModel =
    val tab = view.securityTab

    val securityPoliciesProperties = new SecurityPoliciesProperties(tab.securityPoliciesListProperty, tab.securityPoliciesListSelectedProperty, tab.securityPoliciesDescriptionProperty)

    val credentialsSourceProperties = new CredentialsSourceProperties(tab.credentialsSourceListProperty, tab.credentialsSourceListSelectedProperty, tab.credentialsSourceDescriptionProperty)

    val securityViewModel = new SecurityViewModel(securityPoliciesProperties, credentialsSourceProperties)

    tab.bindOnPolicyChange((o: ObservableValue[_ <: SecurityPolicyEntry], oldValue: SecurityPolicyEntry, newValue: SecurityPolicyEntry) => tab.securityPoliciesDescriptionProperty.value = newValue.description)
    tab.bindOnCredentialsSourceChange((o: ObservableValue[_ <: CredentialsSourceEntry], oldValue: CredentialsSourceEntry, newValue: CredentialsSourceEntry) => tab.credentialsSourceDescriptionProperty.value = newValue.description)

    securityViewModel


  def bindAttackTab(view: AuthsimViewSFX, viewModel: AuthsimViewModelDeferedProxy): AttackViewModel =
    val tab = view.attackTab

    val attackSequenceProperties = new AttackSequenceProperties(
      tab.attackSequenceListProperty,
      tab.attackSequenceListSelectedValueProperty,
      tab.attackSequenceDescriptionProperty,
      tab.attackLogProperty
    )

    val attackViewModel = new AttackViewModel(attackSequenceProperties)

    tab.bindOnSequenceChange((o: ObservableValue[_ <: AttackSequenceEntry], oldValue: AttackSequenceEntry, newValue: AttackSequenceEntry) => tab.attackSequenceDescriptionProperty.value = newValue.description)
    tab.bindOnAttackLaunch((e: ActionEvent) => viewModel.launchAttack())

    attackViewModel



