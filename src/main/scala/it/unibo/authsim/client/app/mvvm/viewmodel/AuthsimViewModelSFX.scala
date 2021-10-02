package it.unibo.authsim.client.app.mvvm.viewmodel

import it.unibo.authsim.client.app.mvvm.binder.{ModelBinder, ViewPropertiesBinder}
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimViewSFX
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityPolicy}
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.client.app.mvvm.simulation.AttackSimulation
import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackSequenceEntry
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import it.unibo.authsim.library.user.model.User
import javafx.collections.ObservableList
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer

object AuthsimViewModelSFX:
  val ATTACK_MISSING_VALUE_TEXT = "Please, make sure to have at least one user, select a policy, a credentials source and an attack procedure before initiating an attack!"

/**
 * Binds View with Model via the ViewModel layer. ScalaFx implementation.
 * @param view view
 * @param model model
 */
class AuthsimViewModelSFX(private val usersViewModel: UsersViewModel,
                          private val securityViewModel: SecurityViewModel,
                          private val attackViewModel: AttackViewModel,
                          private val model: AuthsimModel) extends AuthsimViewModel:

  ModelBinder.bindUsersViewModel(model.usersModel, usersViewModel)
  ModelBinder.bindSecurityViewModel(model.securityModel, securityViewModel)
  ModelBinder.bindAttackViewModel(model.attackModel, attackViewModel)

  override def saveUser(): Unit =
    val username = usersViewModel.addUserFormProperties.usernameProperty.getValue()
    val password = usersViewModel.addUserFormProperties.passwordProperty.getValue()

    if username != null && !username.isBlank && password != null && !password.isBlank then
      val user = User(username, password)
      model.usersModel.usersList += user


  override def generateUsers(): Unit =
    val quantity = usersViewModel.generateUsersFormProperties.quantityProperty.getValue();
    val preset = usersViewModel.generateUsersFormProperties.presetProperty.getValue();

    val credentialsPolicies = SecurityPolicy.Default.credentialsPoliciesFrom(preset)
    if(credentialsPolicies.isDefined) then
      println(credentialsPolicies) // TODO finish user generation


  override def deleteSelectedUsers(): Unit =
    val userEntry = usersViewModel.usersListProperties.usersListSelectedProperty.getValue
    if userEntry != null then
      val user = User(userEntry.username, userEntry.password)
      model.usersModel.usersList -= user


  override def resetUsers(): Unit =
    val usersList = usersViewModel.usersListProperties.usersListProperty.value.clear()
    model.usersModel.usersList.clear()


  override def launchAttack(): Unit =
    val users = model.usersModel.usersList.value
    val policy = model.securityModel.selectedSecurityPolicy
    val credentialsSource = model.securityModel.selectedCredentialsSource
    val selectedProcedure = model.attackModel.selectedAttackSequence

    if !users.isEmpty && policy.nonEmpty && credentialsSource.nonEmpty && selectedProcedure.nonEmpty then
      attackViewModel.attackSequenceProperties.attackLog.value = ""

      val simulation = new AttackSimulation(users, policy.get.policy, credentialsSource.get.source, selectedProcedure.get.sequence)
      simulation.messageProperty().addListener((observable, oldValue, newValue) => attackViewModel.attackSequenceProperties.attackLog.value += newValue)

      new Thread(simulation).start()
    else
      attackViewModel.attackSequenceProperties.attackLog.value = AuthsimViewModelSFX.ATTACK_MISSING_VALUE_TEXT



