package it.unibo.authsim.client.app.mvvm.view.tabs

import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.control.{Label, ListView, SplitPane, TextArea}
import scalafx.scene.layout.HBox

class AttackTab extends VBox {

  val attackSequenceList = makeAttackSequenceList()
  val attackSequenceDescription = makeAttackSequenceDescription()

  val attackLog = makeAttackLog()

  val launchAttackButton = makeLauchAttackButton()

  def makeAttackSequenceList(): ListView[String] = {
    new ListView[String]() {
      items = ObservableBuffer[String](
        "Foo",
        "Bar",
        "Fizz"
      )
    }
  }

  def makeAttackSequenceDescription(): TextArea = {
    new TextArea() {
      text = "Lorem Ipsum"
      editable = false;
    }
  }

  def makeAttackLog(): TextArea = {
    new TextArea() {
      text = "Lorem Ipsum"
      editable = false;
    }
  }

  def makeLauchAttackButton(): Button = {
    new Button("Launch Attack!")
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
        launchAttackButton
      )
    },
  )

}
