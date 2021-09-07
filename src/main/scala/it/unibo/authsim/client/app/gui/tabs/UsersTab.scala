package it.unibo.authsim.client.app.gui.tabs

import it.unibo.authsim.client.app.gui.AuthsimStage.{height, width}
import it.unibo.authsim.client.app.gui.tabs.AddUserForm.{add, hgap, padding, vgap}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{ChoiceBox, Label, ListView, SplitPane, TextField, TextFormatter}
import scalafx.scene.layout.{BorderPane, GridPane, Pane, VBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, FontWeight, Text}
import scalafx.util.converter.FormatStringConverter
import scalafx.scene.control.TextFormatter.Change
import scalafx.collections.ObservableBuffer;

object UsersTab extends SplitPane {

  items.add(new VBox {
    children = Seq(
      AddUserForm,
      GenerateUsersForm
    )
  }
  )

  items.add(new VBox {
    children = Seq(
      UsersList
    )
  })

}

object AddUserForm extends GridPane {
  alignment = Pos.Center;
  hgap = 10;
  vgap = 10;
  padding = Insets(25, 25, 25, 25);

  val scenetitle = new Text("Add User")
  scenetitle.setFont(Font.font("Tahoma", FontWeight.Normal, 20))
  add(scenetitle, 0, 0, 2, 1)

  add(new Label("Username"), 0, 1)

  val usernameField = new TextField();
  add(usernameField, 1, 1)

  add(new Label("Password"), 0, 2)

  val passwordField = new TextField();
  add(passwordField, 1, 2)
}


object GenerateUsersForm extends GridPane {
  alignment = Pos.Center;
  hgap = 10;
  vgap = 10;
  padding = Insets(25, 25, 25, 25);

  val scenetitle = new Text("Generate Users")
  scenetitle.setFont(Font.font("Tahoma", FontWeight.Normal, 20))
  add(scenetitle, 0, 0, 2, 1)

  add(new Label("Quantity"), 0, 1)

  //TODO refactor and bring this to a util
  val numberFilter: Change => Change = (change: Change) => {
    val text = change.text

    if (text.matches("[0-9]*")) then
      change
    else
      null
  }

  val quantityField = new TextField {
    textFormatter = new TextFormatter(numberFilter)
  };
  add(quantityField, 1, 1)

  add(new Label("Preset"), 0, 2)

  // TODO define a generation preset enum in library
  val presetSelect = new ChoiceBox[String] {
    items = ObservableBuffer[String](
      "Preset1",
      "Preset2",
      "Preset3"
    )
  }
  add(presetSelect, 1, 2)
}

object UsersList extends VBox {
  children = Seq(
    new Label("Created Users"),
    new ListView[String]() {
      items = ObservableBuffer[String](
        "User1 Password1",
        "User2 Password2",
        "User3 Password3"
      )
    }
  )
}