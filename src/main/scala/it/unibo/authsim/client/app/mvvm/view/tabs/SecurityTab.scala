package it.unibo.authsim.client.app.mvvm.view.tabs

import scalafx.scene.layout.HBox
import scalafx.scene.control.{Label, ListView, SplitPane, TextArea, TextField}
import scalafx.scene.layout.VBox
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos

class SecurityTab extends VBox {

  val securityPoliciesList = makeSecurityPoliciesList()
  val securityPolicyDescription = makeSecurityPolicyDescription()

  val credentialsSourceList = makeCredentialsSourceList()
  val credentialsSourceDescription = makeCredentialsSourceDescription()

  def makeSecurityPoliciesList(): ListView[String] = {
    new ListView[String]() {
      items = ObservableBuffer[String](
        "Foo",
        "Bar",
        "Fizz"
      )
    }
  }

  def makeSecurityPolicyDescription(): TextArea = {
    new TextArea() {
      text = "Lorem Ipsum"
      editable = false;
    }
  }

  def makeCredentialsSourceList(): ListView[String] = {
    new ListView[String]() {
      items = ObservableBuffer[String](
        "Foo",
        "Bar",
        "Fizz"
      )
    }
  }

  def makeCredentialsSourceDescription(): TextArea = {
    new TextArea() {
      text = "Lorem Ipsum"
      editable = false;
    }
  }

  children = Seq(
    new HBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Security Policies")
      )
    },
    new SplitPane() {
      items.add(securityPoliciesList)
      items.add(securityPolicyDescription)
    },
    new HBox() {
      alignment = Pos.Center
      children = Seq(
        new Label("Credentials Source")
      )
    },
    new SplitPane() {
      items.add(credentialsSourceList)
      items.add(credentialsSourceDescription)
    }
  )

}
