package it.unibo.authsim.client.app.mvvm.viewmodel

import it.unibo.authsim.client.app.mvvm.binder.{ModelBinder, ViewPropertiesBinder}
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimView
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.User
import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackSequenceEntry
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import javafx.collections.ObservableList
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer

object AuthsimViewModel:
  val ATTACK_MISSING_VALUE_TEXT = "Please, make sure to have at least one user, select a policy, a credentials source and an attack procedure before initiating an attack!"

/**
 * Binds View with Model via the ViewModel layer
 * @param view view
 * @param model model
 */
class AuthsimViewModel(private val view: AuthsimView, private val model: AuthsimModel):

  private val usersViewModel: UsersViewModel = ViewPropertiesBinder.bindUsersTab(view, this)
  ModelBinder.bindUsersViewModel(model.usersModel, usersViewModel)

  private val securityViewModel: SecurityViewModel = ViewPropertiesBinder.bindSecurityTab(view, this)
  ModelBinder.bindSecurityViewModel(model.securityModel, securityViewModel)

  private val attackViewModel: AttackViewModel = ViewPropertiesBinder.bindAttackTab(view, this)
  ModelBinder.bindAttackViewModel(model.attackModel, attackViewModel)

  def saveUser(): Unit =
    val username = usersViewModel.addUserFormProperties.usernameProperty.getValue()
    val password = usersViewModel.addUserFormProperties.passwordProperty.getValue()

    if username != null && !username.isBlank && password != null && !password.isBlank then
      val user = new User(username, password)
      model.usersModel.usersList += user


  def generateUsers(): Unit =
    val quantity = usersViewModel.generateUsersFormProperties.quantityProperty.getValue();
    val preset = usersViewModel.generateUsersFormProperties.presetProperty.getValue();

    println(quantity + " " + preset) // TODO hook client when ready
    val securityPolicyDefaultSelected: Option[SecurityPolicy.Default] = SecurityPolicy.Default.values.find(_.policy.name == preset)
    if securityPolicyDefaultSelected.isDefined
    then
      val credentialsPolicies = securityPolicyDefaultSelected.get.policy.credentialPolicies //TODO: use to generate Users


  def deleteSelectedUsers(): Unit =
    val userEntry = usersViewModel.usersListProperties.usersListSelectedProperty.getValue
    if userEntry != null then
      val user = new User(userEntry.username, userEntry.password)
      model.usersModel.usersList -= user


  def resetUsers(): Unit =
    val usersList = usersViewModel.usersListProperties.usersListProperty.value.clear()
    model.usersModel.usersList.clear()


  def launchAttack(): Unit =
    val users = usersViewModel.usersListProperties.usersListProperty.value
    val policy = securityViewModel.securityPoliciesProperties.securityPoliciesListSelectedValue.getValue
    val credentialsSource = securityViewModel.credentialsSourceProperties.credentialsSourceListSelectedValue.getValue
    val selectedProcedure = attackViewModel.attackSequenceProperties.attackSequenceListSelectedValue.getValue

    // TODO hook client when ready
    if users != null && !users.isEmpty && policy != null && credentialsSource != null && selectedProcedure != null then
      attackViewModel.attackSequenceProperties.attackLog.value = " " + users + " " + policy + " " + credentialsSource + " " + selectedProcedure
    else
      attackViewModel.attackSequenceProperties.attackLog.value = AuthsimViewModel.ATTACK_MISSING_VALUE_TEXT



