package it.unibo.authsim.client.app.mvvm.view.tabs.users

import it.unibo.authsim.client.app.mvvm.model.security.SecurityPolicy
import it.unibo.authsim.client.app.mvvm.view.AuthsimViewSFX
import it.unibo.authsim.client.app.mvvm.view.tabs.users.{AddUserForm, GenerateUsersForm, UsersList}
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.CheckBox
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ChoiceBox, Label, ListView, SplitPane, TextArea, TextField, TextFormatter}
import scalafx.scene.control.TextFormatter.Change
import scalafx.scene.layout.{GridPane, HBox, VBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, FontWeight, Text}
import scalafx.util.converter.FormatStringConverter
import scalafx.Includes.eventClosureWrapperWithParam

/**
 * UI element representing Users View
 */
class UsersTab() extends SplitPane :

  private val usernameField = new TextField()
  private val passwordField = new TextField()
  private val saveButton = new Button("Save")

  private val quantityField = AuthsimViewSFX.makeNumberTextField()

  private val presetSelect = new ChoiceBox[UserGenerationPreset]
  private val generateButton = new Button("Generate")

  private val usersList = new ListView[UserEntry]()
  private val deleteSelectedButton = new Button("Delete Selected")
  private val resetButton = new Button("Reset")
  private val presetDescription = new TextArea()

  val usernameProperty: StringProperty = usernameField.text
  val passwordProperty: StringProperty = passwordField.text
  val quantityProperty: StringProperty = quantityField.text
  val presetListProperty: ObjectProperty[ObservableList[UserGenerationPreset]] = presetSelect.items
  val presetProperty: ObjectProperty[UserGenerationPreset] = presetSelect.value
  val usersListProperty: ObjectProperty[ObservableList[UserEntry]] = usersList.items
  val usersListSelectedProperty: ReadOnlyObjectProperty[UserEntry] = usersList.selectionModel.value.selectedItemProperty()
  val presetDescriptionProperty: StringProperty = presetDescription.text

  items.add(makeUserCreationPane())
  items.add(makeUserManagementPane())

  private def makeUserCreationPane(): VBox = new VBox {
    children = Seq(
      new AddUserForm(usernameField, passwordField, saveButton),
      new GenerateUsersForm(quantityField, presetSelect, generateButton)
    )
  }

  private def makeUserManagementPane(): VBox = new VBox {
    children = Seq(
      new UsersList(usersList, deleteSelectedButton, resetButton),
      new PresetDescription(presetDescription)
    )
  }

  def fireSelectUser(position: Int): Unit = usersList.selectionModel.value.select(position)

  def fireSave(): Unit = saveButton.fire()
  def bindOnSave(handler: EventHandler[javafx.event.ActionEvent]) = saveButton.setOnAction(handler)

  def fireGenerate(): Unit = generateButton.fire()
  def bindOnGenerate(handler: EventHandler[javafx.event.ActionEvent]) = generateButton.setOnAction(handler)

  def fireDelete(): Unit = deleteSelectedButton.fire()
  def bindOnDeleteSelected(handler: EventHandler[javafx.event.ActionEvent]) = deleteSelectedButton.setOnAction(handler)

  def fireReset(): Unit = resetButton.fire()
  def bindOnReset(handler: EventHandler[javafx.event.ActionEvent]) = resetButton.setOnAction(handler)

class AddUserForm(usernameField: TextField, passwordField: TextField, saveButton: Button) extends GridPane :
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



class GenerateUsersForm(quantityField: TextField, presetSelect: ChoiceBox[UserGenerationPreset], generateButton: Button) extends GridPane :
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


class UsersList(usersList: ListView[UserEntry], deleteSelectedButton: Button, resetButton: Button) extends VBox :

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

class PresetDescription(presetDescription: TextArea) extends VBox:

  children = Seq(
    new HBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("User Generation Preset Description")
      )
    },
    presetDescription
  )
