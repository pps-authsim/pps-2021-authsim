package it.unibo.authsim.client.app

import it.unibo.authsim.client.app.mvvm.binder.BoundTabsFactory
import it.unibo.authsim.client.app.mvvm.view.AuthsimTabbedStageView
import it.unibo.authsim.client.app.mvvm.view.tabs.UsersTab
import it.unibo.authsim.client.app.mvvm.viewmodel.ViewModel
import scalafx.application.JFXApp3
import scalafx.beans.property.DoubleProperty
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Tab, TabPane}
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{BorderPane, HBox, Priority, VBox}
import scalafx.scene.paint.*
import scalafx.scene.paint.Color.*
import scalafx.scene.text.{Font, Text}

/**
 * Defines the entrypoint to
 */
object AuthsimApp extends JFXApp3 {

  private val viewModel = new ViewModel();

  override def start(): Unit = {
    // TODO launch computations thread
    stage = new AuthsimTabbedStageView(makeBoundModularTabs())
  }

  private def makeBoundModularTabs(): Seq[Tab] = {
    val binder = new BoundTabsFactory(viewModel)
    val usersTab = binder.makeBoundUsersTab()
    val securityTab = binder.makeSecurityTab()
    val attackTab = binder.makeAttackTab()
    return Seq(
      usersTab,
      securityTab,
      attackTab
    )
  }

}