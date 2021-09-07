package it.unibo.authsim.client.app.gui

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, VBox}

object AuthsimStage extends JFXApp3.PrimaryStage {

  title = "AuthSim"
  width = 1024
  scene = new Scene {
    root = new VBox {
      children = Seq(
        AuthsimTabs
      )
    }
  }

}
