package it.unibo.authsim.client.app

import it.unibo.authsim.client.app.gui.AuthsimStage
import it.unibo.authsim.client.app.gui.tabs.UsersTab
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

object AuthsimApp extends JFXApp3 {

  override def start(): Unit = {
    stage = AuthsimStage
  }

}