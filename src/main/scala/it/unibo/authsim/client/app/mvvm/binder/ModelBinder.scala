package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.attack.{AttackModel, AttackSequence}
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityModel, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.{User, UsersModel}
import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackSequenceEntry
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import javafx.collections.ObservableList

/**
 * Helper object for binding model and viewModel dynamic callbacks
 */
object ModelBinder:

  def bindUsersViewModel(usersModel: UsersModel, viewModel: UsersViewModel): Unit =
    val username = "user"
    val password = "password"

    ModelBinder.bindPropertiesWithObservableList(usersModel.usersList, viewModel.usersListProperties.usersListProperty.value, user => new UserEntry(user.username, user.password))
    usersModel.usersList += new User(username, password)


  def bindSecurityViewModel(securityModel: SecurityModel, viewModel: SecurityViewModel): Unit =
    val policyName = "policy1"
    val policyDescription = "This is a simple policy"
    val anotherPolicyName = "policy2"
    val anotherPolicyDescription = "Yet another policy description"

    val credentialsSource = "source1"
    val credentialsSourceDescription = "This is a cred source"
    val anotherCredentialsSource = "source2"
    val anotherCredentialsSourceDescription = "This is another cred source"

    ModelBinder.bindPropertiesWithObservableList(securityModel.securityPolicyList, viewModel.securityPoliciesProperties.securityPoliciesList.value, policy => new SecurityPolicyEntry(policy.policy, policy.description))
    securityModel.securityPolicyList += new SecurityPolicy(policyName, policyDescription)
    securityModel.securityPolicyList += new SecurityPolicy(anotherPolicyName, anotherPolicyDescription)

    ModelBinder.bindPropertiesWithObservableList(securityModel.credentialsSourceList, viewModel.credentialsSourceProperties.credentialsSourceList.value, source => new CredentialsSourceEntry(source.source, source.description))
    securityModel.credentialsSourceList += new CredentialsSource(credentialsSource, credentialsSourceDescription)
    securityModel.credentialsSourceList += new CredentialsSource(anotherCredentialsSource, anotherCredentialsSourceDescription)

  def bindAttackViewModel(attackModel: AttackModel, viewModel: AttackViewModel): Unit =
    val sequenceName = "Sequence1"
    val sequenceDescription = "An attack sequence"
    val anotherSequenceName = "Sequence2"
    val anotherSequenceDescription = "Even better attack sequence"

    ModelBinder.bindPropertiesWithObservableList(attackModel.attackSequenceList, viewModel.attackSequenceProperties.attackSequenceList.value, (sequence => new AttackSequenceEntry(sequence.sequence, sequence.description)))
    attackModel.attackSequenceList += new AttackSequence(sequenceName, sequenceDescription)
    attackModel.attackSequenceList += new AttackSequence(anotherSequenceName, anotherSequenceDescription)

  private def bindPropertiesWithObservableList[A, B](observableListBuffer: ObservableListBuffer[A], propertiesList: ObservableList[B], mapper: (A => B)) =
    observableListBuffer.onAdd(o => propertiesList.add(mapper.apply(o)))
    observableListBuffer.onRemove(o => propertiesList.remove(mapper.apply(o)))


