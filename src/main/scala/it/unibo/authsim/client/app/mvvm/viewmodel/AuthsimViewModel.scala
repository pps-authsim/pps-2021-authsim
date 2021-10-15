package it.unibo.authsim.client.app.mvvm.viewmodel

/**
 * Binds View with Model via the ViewModel layer. This trair defines the avaiable interactions with the application.
 * @param view view
 * @param model model
 */
trait AuthsimViewModel:

  def saveUser(): Unit

  def generateUsers(): Unit

  def deleteSelectedUsers(): Unit

  def resetUsers(): Unit

  def launchAttack(): Unit
  
  def stopAttack(): Unit
