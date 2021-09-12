package it.unibo.authsim.client.app.mvvm.view

import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import it.unibo.authsim.client.app.mvvm.view.tabs.{AttackTab, SecurityTab}
import scalafx.application.JFXApp3
import scalafx.scene.{Node, Scene}
import scalafx.scene.control.{Tab, TabPane}
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

}

/**
 * Abstracts ScalaFx tabbed view creation
 * @param tabModules - a sequence of tabs show at the primary window
 */
class AuthsimView(
                              val usersTab: UsersTab,
                              val securityTab: SecurityTab,
                              val attackTab: AttackTab
                            ) extends JFXApp3.PrimaryStage {



  title = AuthsimView.windowTitle
  width = AuthsimView.startingWindowWidth
  scene = AuthsimView.makeSceneFromTabs(Seq(
    makeTab("Users", usersTab),
    makeTab("Security", securityTab),
    makeTab("Attack", attackTab)
  ));

  private def makeTab(title: String, tabContent: Node): Tab = {
    return new Tab {
      text = title
      closable = false
      content = tabContent
    }
  }

}



