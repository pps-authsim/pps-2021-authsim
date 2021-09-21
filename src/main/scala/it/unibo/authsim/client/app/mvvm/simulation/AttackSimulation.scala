package it.unibo.authsim.client.app.mvvm.simulation

import it.unibo.authsim.client.app.mvvm.model.attack.AttackSequence
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.User
import javafx.concurrent.Task

import scala.collection.mutable.ListBuffer

class AttackSimulation(
                        val users: ListBuffer[User],
                        val policy: SecurityPolicy,
                        val credentialsSource: CredentialsSource,
                        val attackSequence: AttackSequence
                      ) extends Task[Unit]:

  override def call(): Unit =
    val text =  " " + users + " " + policy + " " + credentialsSource + " " + attackSequence
    updateMessage(text)



