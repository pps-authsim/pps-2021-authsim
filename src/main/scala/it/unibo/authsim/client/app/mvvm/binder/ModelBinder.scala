package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.attack.{AttackModel, AttackSequence}
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityModel, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackSequenceEntry
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import javafx.beans.property.ReadOnlyProperty
import it.unibo.authsim.library.user.model.User
import javafx.collections.ObservableList
import scalafx.collections.ObservableBuffer

/**
 * Helper object for binding model and viewModel dynamic callbacks
 */
object ModelBinder:

  def bindUsersViewModel(usersModel: UsersModel, viewModel: UsersViewModel): Unit =
    val username = "user"
    val password = "password"

    val presets = SecurityPolicy.Default.withoutProtocol map {_.policy.name}

    ModelBinder.bindPropertiesWithObservableList(usersModel.presetsList, viewModel.generateUsersFormProperties.presetListProperty.value, preset => preset)
    ModelBinder.bindPropertiesWithObservableList(usersModel.usersList, viewModel.usersListProperties.usersListProperty.value, user => new UserEntry(user.username, user.password))

    usersModel.usersList += User(username, password)

    presets.foreach(preset => usersModel.presetsList += preset)


  def bindSecurityViewModel(securityModel: SecurityModel, viewModel: SecurityViewModel): Unit =
    val credentialsSource = CredentialsSourceType.Sql
    val credentialsSourceDescription = CredentialsSourceType.Sql.description
    val anotherCredentialsSource = CredentialsSourceType.Mongo
    val anotherCredentialsSourceDescription = CredentialsSourceType.Mongo.description

    ModelBinder.bindPropertiesWithObservableList(securityModel.securityPolicyList, viewModel.securityPoliciesProperties.securityPoliciesList.value, policy => new SecurityPolicyEntry(policy.policy, policy.description))
    SecurityPolicy.Default.all.foreach(securityModel.securityPolicyList += _)

    ModelBinder.bindPropertiesWithObservableList(securityModel.credentialsSourceList, viewModel.credentialsSourceProperties.credentialsSourceList.value, source => new CredentialsSourceEntry(source.source, source.description))
    viewModel.credentialsSourceProperties.credentialsSourceListSelectedValue.addListener((observable, oldValue, newValue) => securityModel.selectedCredentialsSource =
      Option(new CredentialsSource(newValue.source, newValue.description))
    )

    securityModel.credentialsSourceList += new CredentialsSource(credentialsSource, credentialsSourceDescription)
    securityModel.credentialsSourceList += new CredentialsSource(anotherCredentialsSource, anotherCredentialsSourceDescription)

  def bindAttackViewModel(attackModel: AttackModel, viewModel: AttackViewModel): Unit =
    val sequenceName = "Sequence1"
    val sequenceDescription = "An attack sequence"
    val anotherSequenceName = "Sequence2"
    val anotherSequenceDescription = "Even better attack sequence"

    ModelBinder.bindPropertiesWithObservableList(attackModel.attackSequenceList, viewModel.attackSequenceProperties.attackSequenceList.value, (sequence => new AttackSequenceEntry(sequence.sequence, sequence.description)))
    viewModel.attackSequenceProperties.attackSequenceListSelectedValue.addListener((observable, oldValue, newValue) => attackModel.selectedAttackSequence =
      Option(new AttackSequence(newValue.sequence, newValue.description))
    )

    attackModel.attackSequenceList += new AttackSequence(sequenceName, sequenceDescription)
    attackModel.attackSequenceList += new AttackSequence(anotherSequenceName, anotherSequenceDescription)

  private def bindPropertiesWithObservableList[A, B](observableListBuffer: ObservableListBuffer[A], propertiesList: ObservableList[B], mapper: (A => B)) =
    observableListBuffer.addOnAddSubscriber(o => propertiesList.add(mapper.apply(o)))
    observableListBuffer.addOnRemoveSubscriber(o => propertiesList.remove(mapper.apply(o)))


