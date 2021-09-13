package it.unibo.authsim.client.app

import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimView
import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModel
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.client.app.mvvm.binder.ViewModelBinder
import it.unibo.authsim.client.app.mvvm.model.attack.AttackModel
import it.unibo.authsim.client.app.mvvm.model.security.SecurityModel
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackTab
import it.unibo.authsim.client.app.mvvm.view.tabs.security.SecurityTab
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
    val securityModel = new SecurityModel
    val attackModel = new AttackModel

    val view = new AuthsimView(usersTab, securityTab, attackTab)
    val model = new AuthsimModel(usersModel, securityModel, attackModel)
    
    val viewModel = new AuthsimViewModel(model)

    val tabsBinder = new ViewModelBinder(view, viewModel)
    tabsBinder.bind();

    stage = view
  }

}