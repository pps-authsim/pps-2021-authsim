package it.unibo.authsim.client.app

import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimView
import it.unibo.authsim.client.app.mvvm.view.tabs.{AttackTab, SecurityTab}
import it.unibo.authsim.client.app.mvvm.viewmodel.ViewModel
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.client.app.mvvm.binder.TabsBinder
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import scalafx.application.JFXApp3
import scalafx.beans.property.DoubleProperty
import scalafx.geometry.Insets
import scalafx.scene.{Node, Scene}
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

  override def start(): Unit = {
    // TODO launch ViewModel on a thread

    val usersTab = new UsersTab
    val securityTab = new SecurityTab
    val attackTab = new AttackTab

    val usersModel = new UsersModel

    val view = new AuthsimView(usersTab, securityTab, attackTab)
    val model = new AuthsimModel(usersModel)
    val viewModel = new ViewModel(model)

    val tabsBinder = new TabsBinder(viewModel)
    tabsBinder.bindUsersTab(usersTab)
    tabsBinder.bindSecurityTab(securityTab)
    tabsBinder.bindAttackTab(attackTab)

    stage = view
  }

}