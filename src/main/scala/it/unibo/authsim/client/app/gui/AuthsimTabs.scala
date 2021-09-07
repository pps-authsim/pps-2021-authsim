package it.unibo.authsim.client.app.gui

import it.unibo.authsim.client.app.gui.tabs.{SecurityTab, UsersTab}
import scalafx.scene.control.{Label, SplitPane, Tab, TabPane}
import scalafx.scene.layout.{Pane, VBox}

object AuthsimTabs extends TabPane {

  tabs = Seq(
    new Tab {
      text = "Users"
      closable = false
      content = UsersTab
    },
    new Tab {
      text = "Security"
      closable = false
      content = SecurityTab
    },
    new Tab {
      text = "Attack"
      closable = false
    }
  )
}
