package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.attack.{AttackModel, AttackSequence}
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityModel, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.library.user.model.User

import scala.collection.mutable.ListBuffer

object ModelInitializer:

  def initializeUsersModel(usersModel: UsersModel): Unit =
    val username = "user"
    val password = "password"

    val presets = SecurityPolicy.Default.withoutProtocol

    usersModel.usersList += User(username, password)
    presets.foreach(preset => usersModel.presetsList += preset)

  def initializeSecurityModel(securityModel: SecurityModel): Unit =
    val sqlSource = CredentialsSourceType.Sql
    val sqlSourceDescription = CredentialsSourceType.Sql.description
    val mongoSource = CredentialsSourceType.Mongo
    val mongoSourceDescription = CredentialsSourceType.Mongo.description

    SecurityPolicy.Default.all.foreach(securityModel.securityPolicyList += _)

    securityModel.credentialsSourceList += new CredentialsSource(sqlSource, sqlSourceDescription)
    securityModel.credentialsSourceList += new CredentialsSource(mongoSource, mongoSourceDescription)

  def initializeAttackModel(attackModel: AttackModel): Unit =
    configuredAttacks().foreach(attack => attackModel.attackSequenceList += attack)

  private def configuredAttacks(): Seq[AttackSequence] =
    var sequence = ListBuffer[AttackSequence]()
    sequence += new AttackSequence("BruteForce Simple", "")
    sequence += new AttackSequence("BruteForce Advanced", "")
    sequence.toSeq