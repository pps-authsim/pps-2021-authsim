package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.attack.{AttackModel, AttackSequence}
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityModel, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.library.user.model.User

object ModelInitializer:

  def initializeUsersModel(usersModel: UsersModel): Unit =
    val username = "user"
    val password = "password"

    val presets = SecurityPolicy.Default.withoutProtocol map {_.policy.name}

    usersModel.usersList += User(username, password)
    presets.foreach(preset => usersModel.presetsList += preset)

  def initializeSecurityModel(securityModel: SecurityModel): Unit =
    val credentialsSource = CredentialsSourceType.Sql
    val credentialsSourceDescription = CredentialsSourceType.Sql.description
    val anotherCredentialsSource = CredentialsSourceType.Mongo
    val anotherCredentialsSourceDescription = CredentialsSourceType.Mongo.description

    SecurityPolicy.Default.all.foreach(securityModel.securityPolicyList += _)

    securityModel.credentialsSourceList += new CredentialsSource(credentialsSource, credentialsSourceDescription)
    securityModel.credentialsSourceList += new CredentialsSource(anotherCredentialsSource, anotherCredentialsSourceDescription)

  def initializeAttackModel(attackModel: AttackModel): Unit =
    val sequenceName = "Sequence1"
    val sequenceDescription = "An attack sequence"
    val anotherSequenceName = "Sequence2"
    val anotherSequenceDescription = "Even better attack sequence"

    attackModel.attackSequenceList += new AttackSequence(sequenceName, sequenceDescription)
    attackModel.attackSequenceList += new AttackSequence(anotherSequenceName, anotherSequenceDescription)

