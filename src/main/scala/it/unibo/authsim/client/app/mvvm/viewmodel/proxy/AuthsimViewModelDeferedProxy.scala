package it.unibo.authsim.client.app.mvvm.viewmodel.proxy

import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModel

import scala.util.{Failure, Success}

class AuthsimViewModelDeferedProxy() extends AuthsimViewModel:

  var delegate: Option[AuthsimViewModel] = Option.empty

  def saveUser(): Unit =
    delegate.map(delegate => delegate.saveUser())

  def generateUsers(): Unit =
    delegate.map(delegate => delegate.generateUsers())

  def deleteSelectedUsers(): Unit =
    delegate.map(delegate => delegate.deleteSelectedUsers())

  def resetUsers(): Unit =
    delegate.map(delegate => delegate.resetUsers())

  def launchAttack(): Unit =
    delegate.map(delegate => delegate.launchAttack())




