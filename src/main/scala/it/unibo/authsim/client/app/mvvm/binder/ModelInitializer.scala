package it.unibo.authsim.client.app.mvvm.binder

import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.attack.{AttackModel, AttackSequence}
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityModel, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.library.user.model.User
import it.unibo.authsim.client.app.simulation.attacks.PreconfiguredAttacks.{AttackConfiguration, BruteForceAll, BruteForceLetters, BruteForceLowers, DictionaryMostCommonPasswords}

import scala.collection.mutable.ListBuffer

object ModelInitializer:

  def initializeUsersModel(usersModel: UsersModel): Unit =
    val presets = SecurityPolicy.Default.withoutProtocol

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
    sequence += new AttackSequence("BruteForce Lowercase Letters", "A Brute Force Attack configured to use lowercase characters to construct the password string with a maximum length of 6", BruteForceLowers)
    sequence += new AttackSequence("BruteForce Only Letters", "A Brute Force Attack configured to use both lower and upper case characters to construct the password string with a maximum length of 10", BruteForceLetters)
    sequence += new AttackSequence("BruteForce Alphanumerical", "A Brute Force Attack configured to use all alphanumeric and symbols characters to construct the password string with a maximum length of 16", BruteForceAll)
    sequence += new AttackSequence("Dictionary Most Common Passwords", "A Dictionary Attack configured to use the dictionary of the top 97 most common passwords, combining them up to 3 times", DictionaryMostCommonPasswords)
    sequence.toSeq