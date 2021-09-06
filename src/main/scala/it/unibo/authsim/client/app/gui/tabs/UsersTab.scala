package it.unibo.authsim.client.app.gui.tabs

import it.unibo.authsim.client.app.gui.AuthsimStage.{height, width}
import scalafx.scene.control.{Label, SplitPane}
import scalafx.scene.layout.{BorderPane, Pane, VBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text

object UsersTab extends SplitPane {

  items.add(new VBox {
    children = Seq(
      AddUserForm,
      GenerateUsersForm
    )
  })

  items.add(new VBox {
    children = Seq(
      UsersList
    )
  })

}

object AddUserForm extends Pane {
  children = Seq(
    new Label("Add User")
  )
}


object GenerateUsersForm extends Pane {
  children = Seq(
    new Label("Generate Users")
  )
}

object UsersList extends Pane {
  children = Seq(
    new Label("Created Users")
  )
}