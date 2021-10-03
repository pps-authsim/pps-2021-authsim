package it.unibo.authsim.client.app

import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimViewSFX
import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModelSFX
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.client.app.mvvm.binder.{ModelBinder, ModelInitializer, ViewPropertiesBinder}
import it.unibo.authsim.client.app.mvvm.model.attack.AttackModel
import it.unibo.authsim.client.app.mvvm.model.security.SecurityModel
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackTab
import it.unibo.authsim.client.app.mvvm.view.tabs.security.SecurityTab
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.proxy.AuthsimViewModelDeferedProxy
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
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
 * Entrypoint for the authsim app
 */
object AuthsimApp extends JFXApp3 :

  override def start(): Unit =

    val view = makeAuthsimView()
    val model = makeAuthsimModel()
    val viewModel = makeAuthsimViewModel(view, model)
    
    initializeModel(model)

    stage = view

  private def makeAuthsimView(): AuthsimViewSFX =
    val usersTab = new UsersTab
    val securityTab = new SecurityTab
    val attackTab = new AttackTab

    new AuthsimViewSFX(usersTab, securityTab, attackTab)

  private def makeAuthsimModel(): AuthsimModel =
    val usersModel = new UsersModel
    val securityModel = new SecurityModel
    val attackModel = new AttackModel

    new AuthsimModel(usersModel, securityModel, attackModel)

  private def makeAuthsimViewModel(view: AuthsimViewSFX, model: AuthsimModel): AuthsimViewModelSFX =
    val viewModelDeferedProxy = new AuthsimViewModelDeferedProxy

    val usersViewModel: UsersViewModel = ViewPropertiesBinder.bindUsersTab(view, viewModelDeferedProxy)
    val securityViewModel: SecurityViewModel = ViewPropertiesBinder.bindSecurityTab(view, viewModelDeferedProxy)
    val attackViewModel: AttackViewModel = ViewPropertiesBinder.bindAttackTab(view, viewModelDeferedProxy)

    val authsimViewModel = new AuthsimViewModelSFX(usersViewModel, securityViewModel, attackViewModel, model)

    viewModelDeferedProxy.delegate = Option(authsimViewModel)

    authsimViewModel

  private def initializeModel(model: AuthsimModel): Unit =
    ModelInitializer.initializeUsersModel(model.usersModel)
    ModelInitializer.initializeSecurityModel(model.securityModel)
    ModelInitializer.initializeAttackModel(model.attackModel)


