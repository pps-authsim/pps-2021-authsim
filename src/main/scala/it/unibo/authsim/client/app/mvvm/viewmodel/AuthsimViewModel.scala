package it.unibo.authsim.client.app.mvvm.viewmodel

/**
 * Binds View with Model via the ViewModel layer. This trait defines the avaiable interactions with the application.
 * @param view view
 * @param model model
 */
trait AuthsimViewModel:

  /**
   * Persists a new user
   */
  def saveUser(): Unit

  /**
   * Generates users from a preset
   */
  def generateUsers(): Unit

  /**
   * Deleted the selected user
   */
  def deleteSelectedUsers(): Unit

  /**
   * Resets currently persisted users
   */
  def resetUsers(): Unit

  /**
   * Launched an attack simulation
   */
  def launchAttack(): Unit

  /**
   * Stops currentl running attack simulation
   */
  def stopAttack(): Unit
