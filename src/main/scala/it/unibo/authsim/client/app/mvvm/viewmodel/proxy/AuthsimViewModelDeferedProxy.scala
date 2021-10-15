package it.unibo.authsim.client.app.mvvm.viewmodel.proxy

import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModel

import scala.util.{Failure, Success}

class AuthsimViewModelDeferedProxy() extends AuthsimViewModel:

  var delegate: Option[AuthsimViewModel] = Option.empty

  override def saveUser(): Unit =
    delegate.map(delegate => delegate.saveUser())

  override def generateUsers(): Unit =
    delegate.map(delegate => delegate.generateUsers())

  override def deleteSelectedUsers(): Unit =
    delegate.map(delegate => delegate.deleteSelectedUsers())

  override def resetUsers(): Unit =
    delegate.map(delegate => delegate.resetUsers())

  override def launchAttack(): Unit =
    delegate.map(delegate => delegate.launchAttack())

  override def stopAttack(): Unit =
    delegate.map(delegate => delegate.stopAttack())



