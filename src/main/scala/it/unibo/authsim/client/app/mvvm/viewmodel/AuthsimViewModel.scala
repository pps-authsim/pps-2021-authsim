package it.unibo.authsim.client.app.mvvm.viewmodel

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

class AuthsimViewModel(
                 private val model: AuthsimModel
               ) :

  var usersViewModel: UsersViewModel = null
  var securityViewModel: SecurityViewModel = null
  var attackViewModel: AttackViewModel = null

  def bindUsersViewModel(usersViewModel: UsersViewModel): Unit = {
    this.usersViewModel = usersViewModel
    val usersModel = model.usersModel;

    val username = "user"
    val password = "password"

    AuthsimModel.bindPropertiesWithObservableList(model.usersModel.usersList, usersViewModel.usersListProperties.usersListProperty.value, user => new UserEntry(user.username, user.password))
    model.usersModel.usersList += new User(username, password)
  }

  def bindSecurityViewModel(securityViewModel: SecurityViewModel): Unit = {
    this.securityViewModel = securityViewModel
    val securityModel = model.securityModel

    // todo hook when client is ready
    val policyName = "policy1"
    val policyDescription = "This is a simple policy"
    val anotherPolicyName = "policy2"
    val anotherPolicyDescription = "Yet another policy description"

    val credentialsSource = "source1"
    val credentialsSourceDescription = "This is a cred source"
    val anotherCredentialsSource = "source2"
    val anotherCredentialsSourceDescription = "This is another cred source"

    AuthsimModel.bindPropertiesWithObservableList(model.securityModel.securityPolicyList, securityViewModel.securityPoliciesProperties.securityPoliciesList.value, policy => new SecurityPolicyEntry(policy.policy, policy.description))
    model.securityModel.securityPolicyList += new SecurityPolicy(policyName, policyDescription)
    model.securityModel.securityPolicyList += new SecurityPolicy(anotherPolicyName, anotherPolicyDescription)

    AuthsimModel.bindPropertiesWithObservableList(model.securityModel.credentialsSourceList, securityViewModel.credentialsSourceProperties.credentialsSourceList.value, source => new CredentialsSourceEntry(source.source, source.description))
    model.securityModel.credentialsSourceList += new CredentialsSource(credentialsSource, credentialsSourceDescription)
    model.securityModel.credentialsSourceList += new CredentialsSource(anotherCredentialsSource, anotherCredentialsSourceDescription)
  }

  def bindAttackViewModel(attackViewModel: AttackViewModel): Unit = {
    // todo hook client when ready

    val sequenceName = "Sequence1"
    val sequenceDescription = "An attack sequence"
    val anotherSequenceName = "Sequence2"
    val anotherSequenceDescription = "Even better attack sequence"

    AuthsimModel.bindPropertiesWithObservableList(model.attackModel.attackSequenceList, attackViewModel.attackSequenceProperties.attackSequenceList.value, (sequence => new AttackSequenceEntry(sequence.sequence, sequence.description)))
    model.attackModel.attackSequenceList += new AttackSequence(sequenceName, sequenceDescription)
    model.attackModel.attackSequenceList += new AttackSequence(anotherSequenceName, anotherSequenceDescription)

    this.attackViewModel = attackViewModel
  }

  def saveUser(): Unit = {
    val username = usersViewModel.addUserFormProperties.usernameProperty.getValue()
    val password = usersViewModel.addUserFormProperties.passwordProperty.getValue()

    if username != null && !username.isBlank && password != null && !password.isBlank then
      val user = new User(username, password)
      model.usersModel.usersList += user
  }

  def generateUsers(): Unit = {
    val quantity = usersViewModel.generateUsersFormProperties.quantityProperty.getValue();
    val preset = usersViewModel.generateUsersFormProperties.presetProperty.getValue();

    println(quantity + " " + preset) // TODO hook client when ready
  }

  def deleteSelectedUsers(): Unit = {
    val userEntry = usersViewModel.usersListProperties.usersListSelectionModel.value.getSelectedItem
    val user = new User(userEntry.username, userEntry.password)

    model.usersModel.usersList -= user
  }

  def resetUsers(): Unit = {
    val usersList = usersViewModel.usersListProperties.usersListProperty.value.clear()
    model.usersModel.usersList = new ObservableListBuffer
    AuthsimModel.bindPropertiesWithObservableList(model.usersModel.usersList, usersViewModel.usersListProperties.usersListProperty.value, user => new UserEntry(user.username, user.password))
  }

  def launchAttack(): Unit = {
    val users = usersViewModel.usersListProperties.usersListProperty.value
    val policy = securityViewModel.securityPoliciesProperties.securityPoliciesListSelectedValue.getValue
    val credentialsSource = securityViewModel.credentialsSourceProperties.credentialsSourceListSelectedValue.getValue
    val selectedProcedure = attackViewModel.attackSequenceProperties.attackSequenceListSelectedValue.getValue

    // TODO hook client when ready
    if users != null && !users.isEmpty && policy != null && credentialsSource != null && selectedProcedure != null then
      attackViewModel.attackSequenceProperties.attackLog.value = " " + users + " " + policy + " " + credentialsSource + " " + selectedProcedure
    else
      attackViewModel.attackSequenceProperties.attackLog.value = AuthsimViewModel.ATTACK_MISSING_VALUE_TEXT
  }


