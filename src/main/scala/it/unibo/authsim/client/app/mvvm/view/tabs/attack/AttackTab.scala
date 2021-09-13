package it.unibo.authsim.client.app.mvvm.view.tabs.attack

import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.control.*
import scalafx.scene.layout.{HBox, VBox}

// todo expose only properties
class AttackTab extends VBox :

  val attackSequenceList = makeAttackSequenceList()
  val attackSequenceDescription = makeAttackSequenceDescription()

  val attackLog = makeAttackLog()

  val launchAttackButton = makeLauchAttackButton()

  def makeAttackSequenceList(): ListView[AttackSequenceEntry] = new ListView[AttackSequenceEntry]() {
    items = ObservableBuffer[AttackSequenceEntry]()
  }

  def makeAttackSequenceDescription(): TextArea = new TextArea() {
    text = ""
    editable = false;
  }

  def makeAttackLog(): TextArea = new TextArea() {
    text = "Lorem Ipsum"
    editable = false;
  }

  def makeLauchAttackButton(): Button = new Button("Launch Attack!")

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
        launchAttackButton
      )
    },
  )


