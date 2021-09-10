package it.unibo.authsim.client.app.mvvm.view

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{Tab, TabPane}
import scalafx.scene.layout.VBox

object AuthsimTabbedStageView {

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
class AuthsimTabbedStageView(private val tabModules: Seq[Tab]) extends JFXApp3.PrimaryStage {

  title = AuthsimTabbedStageView.windowTitle
  width = AuthsimTabbedStageView.startingWindowWidth
  scene = AuthsimTabbedStageView.makeSceneFromTabs(tabModules);

}



