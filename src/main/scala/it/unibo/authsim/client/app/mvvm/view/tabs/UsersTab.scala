package it.unibo.authsim.client.app.mvvm.view.tabs

import javafx.scene.control.CheckBox
import scalafx.scene.layout.HBox
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ChoiceBox, Label, ListView, SplitPane, TextField, TextFormatter}
import scalafx.scene.layout.{BorderPane, GridPane, Pane, VBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, FontWeight, Text}
import scalafx.util.converter.FormatStringConverter
import scalafx.scene.control.TextFormatter.Change
import scalafx.collections.ObservableBuffer;

// TODO refactor for clarity
class UsersTab() extends SplitPane {

  val usernameField = makeUsernameField()
  val passwordField = makePasswordField()
  val saveButton = makeSaveButton()

  val quantityField = makeQuantityField()
  val presetSelect = makePresetSelect()
  val generateButton = makeGenerateButton()

  val usersList = makeUsersListView()
  val deleteSelectedButton = makeDeleteSelectedButton()
  val resetButton = makeResetButton()

  items.add(makeUserCreationPane())
  items.add(makeUserManagementPane())

  def makeUsernameField(): TextField = {
    new TextField();
  }

  def makePasswordField(): TextField = {
    new TextField();
  }

  def makeSaveButton(): Button = {
    new Button("Save");
  }

  def makeQuantityField(): TextField = {
    //TODO refactor and bring this to a util
    val numberFilter: Change => Change = (change: Change) => {
      val text = change.text

      if (text.matches("[0-9]*")) then
        change
      else
        null
    }

    new TextField {
      textFormatter = new TextFormatter(numberFilter)
    };
  }

  def makePresetSelect(): ChoiceBox[String] = {
    // TODO define a generation preset enum in library
    new ChoiceBox[String] {
      items = ObservableBuffer[String](
        "Preset1",
        "Preset2",
        "Preset3"
      )
    }
  }

  def makeGenerateButton(): Button = {
    new Button("Save");
  }

  def makeUsersListView(): ListView[String] = {
    new ListView[String]() {
      items = ObservableBuffer[String](
        "User1 Password1",
        "User2 Password2",
        "User3 Password3"
      )
    }
  }

  def makeDeleteSelectedButton(): Button = {
    new Button("Delete Selected")
  }

  def makeResetButton(): Button = {
    new Button("Reset")
  }

  def makeUserCreationPane(): VBox = {
    new VBox {
      children = Seq(
        new AddUserForm(usernameField, passwordField, saveButton),
        new GenerateUsersForm(quantityField, presetSelect, generateButton)
      )
    }
  }

  def makeUserManagementPane(): VBox = {
    new VBox {
      children = Seq(
        new UsersList(usersList, deleteSelectedButton, resetButton)
      )
    }
  }

}

class AddUserForm(usernameField: TextField, passwordField: TextField, saveButton: Button) extends GridPane {
  alignment = Pos.Center
  hgap = 10
  vgap = 10
  padding = Insets(25, 25, 25, 25)
  val scenetitle = new Text("Add User")
  scenetitle.setFont(Font.font("Tahoma", FontWeight.Normal, 20))
  add(scenetitle, 0, 0, 2, 1)
  add(new Label("Username"), 0, 1)
  add(usernameField, 1, 1)
  add(new Label("Password"), 0, 2)
  add(passwordField, 1, 2)
  add(saveButton, 1, 4)
}


class GenerateUsersForm(quantityField: TextField, presetSelect: ChoiceBox[String], generateButton: Button) extends GridPane {
  alignment = Pos.Center;
  hgap = 10;
  vgap = 10;
  padding = Insets(25, 25, 25, 25);

  val scenetitle = new Text("Generate Users")
  scenetitle.setFont(Font.font("Tahoma", FontWeight.Normal, 20))
  add(scenetitle, 0, 0, 2, 1)

  add(new Label("Quantity"), 0, 1)
  add(quantityField, 1, 1)

  add(new Label("Preset"), 0, 2)
  add(presetSelect, 1, 2)

  add(generateButton, 1, 3)
}

class UsersList(usersList: ListView[String], deleteSelectedButton: Button, resetButton: Button) extends VBox {

  children = Seq(
    new HBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Generated Users")
      )
    },
    usersList,
    new HBox() {
      children = Seq(
        deleteSelectedButton,
        resetButton
      )
    }
  )
}