package it.unibo.authsim.client.app.mvvm.view

import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackTab
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import it.unibo.authsim.client.app.mvvm.view.tabs.security.SecurityTab
import scalafx.application.JFXApp3
import scalafx.scene.control.TextFormatter.Change
import scalafx.scene.{Node, Scene}
import scalafx.scene.control.{Tab, TabPane, TextField, TextFormatter}
import scalafx.scene.layout.VBox

object AuthsimView {

  private val windowTitle = "AuthSim - pps2021"
  private val startingWindowWidth = 1024

  private def makeSceneFromTabs(tabModules: Seq[Tab]): Scene = {
    new Scene {
      root = new VBox {
        children = Seq(
          new TabPane {
            tabs = tabModules
          }
        )
      }
    }
  }

  def makeNumberTextField(): TextField = new TextField {
    val numberFilter: Change => Change = (change: Change) =>
      val text = change.text
      if (text.matches("[0-9]*")) then
        change
      else
        null
    textFormatter = new TextFormatter(numberFilter)
  }

}

/**
 * Application's view consisting of tabbed views
 * @param usersTab users tab
 * @param securityTab security tab
 * @param attackTab attack tab
 */
class AuthsimView(
                              val usersTab: UsersTab,
                              val securityTab: SecurityTab,
                              val attackTab: AttackTab
                            ) extends JFXApp3.PrimaryStage :

  title = AuthsimView.windowTitle
  width = AuthsimView.startingWindowWidth
  scene = AuthsimView.makeSceneFromTabs(Seq(
    makeTab("Users", usersTab),
    makeTab("Security", securityTab),
    makeTab("Attack", attackTab)
  ))

  private def makeTab(title: String, tabContent: Node): Tab = new Tab {
    text = title
    closable = false
    content = tabContent
  }




