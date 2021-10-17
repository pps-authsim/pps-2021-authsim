package it.unibo.authsim.client.app.mvvm.view.tabs.attack

import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry}
import javafx.beans.value.ChangeListener
import javafx.collections.ObservableList
import javafx.event.EventHandler
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.control.*
import scalafx.Includes.jfxReadOnlyObjectProperty2sfx
import scalafx.beans.property.{ObjectProperty, ReadOnlyObjectProperty, StringProperty}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.event.ActionEvent

/**
 * UI element representing Attack View
 */
class AttackTab extends VBox :

  private val attackSequenceList = makeAttackSequenceList()
  private val attackSequenceDescription = makeAttackSequenceDescription()
  private val attackLog = makeAttackLog()
  private val launchAttackButton = new Button("Launch Attack!")
  private val stopAttackButton = new Button("Stop Attack")

  val attackSequenceListProperty: ObjectProperty[ObservableList[AttackSequenceEntry]] = attackSequenceList.items
  val attackSequenceListSelectedValueProperty: ReadOnlyObjectProperty[AttackSequenceEntry] = attackSequenceList.selectionModel.getValue.selectedItemProperty()
  val attackSequenceDescriptionProperty: StringProperty = attackSequenceDescription.text
  val attackLogProperty: StringProperty = attackLog.text

  private def makeAttackSequenceList(): ListView[AttackSequenceEntry] = new ListView[AttackSequenceEntry]() {
    items = ObservableBuffer[AttackSequenceEntry]()
  }

  private def makeAttackSequenceDescription(): TextArea = new TextArea() {
    text = ""
    editable = false
    wrapText = true
  }

  private def makeAttackLog(): TextArea = new TextArea() {
    text = "This is the attack log, the informations regarding the attack are going to be displayed here."
    editable = false
    wrapText = true
  }

  children = Seq(
    new HBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Attack Sequence")
      )
    },
    new SplitPane() {
      items.add(attackSequenceList)
      items.add(attackSequenceDescription)
    },
    new VBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Attack Log"),
        attackLog,
        launchAttackButton,
        stopAttackButton
      )
    },
  )

  def fireSelectSequence(position: Int): Unit = attackSequenceList.selectionModel.value.select(position)
  def bindOnSequenceChange(listener: ChangeListener[AttackSequenceEntry]) = attackSequenceListSelectedValueProperty.addListener(listener)

  def fireAttackLaunch(): Unit = launchAttackButton.fire()
  def bindOnAttackLaunch(handler: EventHandler[javafx.event.ActionEvent]) = launchAttackButton.setOnAction(handler)
  
  def fireStopAttack(): Unit = stopAttackButton.fire()
  def bindOnAttackStop(handler: EventHandler[javafx.event.ActionEvent]) = stopAttackButton.setOnAction(handler)

