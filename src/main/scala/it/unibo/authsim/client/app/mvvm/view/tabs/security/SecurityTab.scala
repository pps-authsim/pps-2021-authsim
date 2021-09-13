package it.unibo.authsim.client.app.mvvm.view.tabs.security

import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.control.*
import scalafx.scene.layout.{HBox, VBox}

class SecurityTab extends VBox :

  val securityPoliciesList = makeSecurityPoliciesList()
  val securityPolicyDescription = makeSecurityPolicyDescription()

  val credentialsSourceList = makeCredentialsSourceList()
  val credentialsSourceDescription = makeCredentialsSourceDescription()

  def makeSecurityPoliciesList(): ListView[SecurityPolicyEntry] = new ListView[SecurityPolicyEntry]() {
    items = ObservableBuffer[SecurityPolicyEntry]()
  }

  def makeSecurityPolicyDescription(): TextArea = new TextArea() {
    text = ""
    editable = false;
  }

  def makeCredentialsSourceList(): ListView[CredentialsSourceEntry] = new ListView[CredentialsSourceEntry]() {
    items = ObservableBuffer[CredentialsSourceEntry]()
  }

  def makeCredentialsSourceDescription(): TextArea = new TextArea() {
    text = ""
    editable = false;
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


