package it.unibo.authsim.client.app.mvvm.viewmodel

import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimView
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.User
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackSequenceEntry
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import javafx.collections.ObservableList
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer

object AuthsimViewModel:
  val ATTACK_MISSING_VALUE_TEXT = "Please, make sure to have at least one user, select a policy, a credentials source and an attack procedure before initiating an attack!"

class AuthsimViewModel(
                 private val model: AuthsimModel
               ) {

  var usersViewModel: UsersViewModel = null
  var securityViewModel: SecurityViewModel = null
  var attackViewModel: AttackViewModel = null

  def bindUsersViewModel(usersViewModel: UsersViewModel): Unit = {
    this.usersViewModel = usersViewModel
    val usersModel = model.usersModel;

    val username = "user"
    val password = "password"

    model.usersModel.usersList += new User(username, password)
    usersViewModel.usersListProperties.usersListProperty.value.add(new UserEntry(username, password))
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

    model.securityModel.securityPolicyList += new SecurityPolicy(policyName, policyDescription)
    securityViewModel.securityPoliciesProperties.securityPoliciesList.value.add(new SecurityPolicyEntry(policyName, policyDescription))
    model.securityModel.securityPolicyList += new SecurityPolicy(anotherPolicyName, anotherPolicyDescription)
    securityViewModel.securityPoliciesProperties.securityPoliciesList.value.add(new SecurityPolicyEntry(anotherPolicyName, anotherPolicyDescription))

    model.securityModel.credentialsSourceList += new CredentialsSource(credentialsSource, credentialsSourceDescription)
    securityViewModel.credentialsSourceProperties.credentialsSourceList.value.add(new CredentialsSourceEntry(credentialsSource, credentialsSourceDescription))
    model.securityModel.credentialsSourceList += new CredentialsSource(anotherCredentialsSource, anotherCredentialsSourceDescription)
    securityViewModel.credentialsSourceProperties.credentialsSourceList.value.add(new CredentialsSourceEntry(anotherCredentialsSource, anotherCredentialsSourceDescription))
  }

  def bindAttackViewModel(attackViewModel: AttackViewModel): Unit = {
    // todo hook client when ready

    val sequenceName = "Sequence1"
    val sequenceDescription = "An attack sequence"
    val anotherSequenceName = "Sequence2"
    val anotherSequenceDescription = "Even better attack sequence"

    model.attackModel.securityPolicyList += new AttackSequenceEntry(sequenceName, sequenceDescription)
    attackViewModel.attackSequenceProperties.attackSequenceList.value.add(new AttackSequenceEntry(sequenceName, sequenceDescription))
    model.attackModel.securityPolicyList += new AttackSequenceEntry(anotherSequenceName, anotherSequenceDescription)
    attackViewModel.attackSequenceProperties.attackSequenceList.value.add(new AttackSequenceEntry(anotherSequenceName, anotherSequenceDescription))

    this.attackViewModel = attackViewModel
  }

  def saveUser(): Unit = {
    val username = usersViewModel.addUserFormProperties.usernameProperty.getValue()
    val password = usersViewModel.addUserFormProperties.passwordProperty.getValue()

    val user = new User(username, password)
    val userEntry = new UserEntry(username, password)

    model.usersModel.usersList += user
    usersViewModel.usersListProperties.usersListProperty.value += userEntry
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
    usersViewModel.usersListProperties.usersListProperty.value.remove(userEntry)
  }

  def resetUsers(): Unit = {
    val usersList = usersViewModel.usersListProperties.usersListProperty.value.clear()
  }

  def launchAttack(): Unit = {
    val users = usersViewModel.usersListProperties.usersListProperty.value
    val policy = securityViewModel.securityPoliciesProperties.securityPoliciesListSelectionModel.value.getSelectedItem
    val credentialsSource = securityViewModel.credentialsSourceProperties.credentialsSourceListSelectionModel.value.getSelectedItem
    val selectedProcedure = attackViewModel.attackSequenceProperties.attackSequenceListSelectionModel.value.getSelectedItem

    // TODO hook client when ready
    if users != null && !users.isEmpty && policy != null && credentialsSource != null && selectedProcedure != null then
      attackViewModel.attackSequenceProperties.attackLog.value = " " + users + " " + selectedProcedure+ " " + policy + " " + credentialsSource + " " + selectedProcedure
    else
      attackViewModel.attackSequenceProperties.attackLog.value = AuthsimViewModel.ATTACK_MISSING_VALUE_TEXT
  }

}

