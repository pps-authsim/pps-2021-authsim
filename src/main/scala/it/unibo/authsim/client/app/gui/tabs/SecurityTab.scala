package it.unibo.authsim.client.app.gui.tabs

import it.unibo.authsim.client.app.gui.tabs.AddUserForm.alignment
import it.unibo.authsim.client.app.gui.tabs.UsersList.children
import scalafx.scene.layout.HBox
import scalafx.scene.control.{Label, ListView, SplitPane, TextArea, TextField}
import scalafx.scene.layout.VBox
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos

// TODO modularize tab
// TODO modularize label + list + textarea blocks with event handling and model binding
object SecurityTab extends VBox {

  children = Seq(
    new HBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Security Policies")
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
    new HBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Credentials Source")
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
    }
  )

}
