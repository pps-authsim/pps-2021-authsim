package it.unibo.authsim.client.app.gui.tabs

import it.unibo.authsim.client.app.gui.tabs.AddUserForm.alignment
import it.unibo.authsim.client.app.gui.tabs.SecurityTab.children
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.control.{Label, ListView, SplitPane, TextArea}
import scalafx.scene.layout.HBox

object AttackTab extends VBox {

  children = Seq(
    new HBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Attack Sequence")
      )
    },
    new SplitPane() {
      items.add(new ListView[String]() {
        items = ObservableBuffer[String](
          "Foo",
          "Bar",
          "Fizz"
        )
      })

      items.add(new TextArea() {
        text = "Lorem Ipsum"
        editable = false;
      })
    },
    new VBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Attack Log"),
        new TextArea() {
          text = "Lorem Ipsum"
          editable = false;
        },
        new Button("Launch Attack!")
      )
    },
  )

}
