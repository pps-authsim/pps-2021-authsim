package it.unibo.authsim.client.app.mvvm.view.tabs.security

import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.value.ChangeListener
import javafx.collections.ObservableList
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.control.*
import scalafx.scene.layout.{HBox, VBox}
import scalafx.beans.property.{ObjectProperty, StringProperty}

class SecurityTab extends VBox :

  private val securityPoliciesList = makeSecurityPoliciesList()
  private val securityPolicyDescription = makeSecurityPolicyDescription()

  private val credentialsSourceList = makeCredentialsSourceList()
  private val credentialsSourceDescription = makeCredentialsSourceDescription()

  private def makeSecurityPoliciesList(): ListView[SecurityPolicyEntry] = new ListView[SecurityPolicyEntry]() {
    items = ObservableBuffer[SecurityPolicyEntry]()
  }

  private def makeSecurityPolicyDescription(): TextArea = new TextArea() {
    text = ""
    editable = false
    wrapText = true
  }

  private def makeCredentialsSourceList(): ListView[CredentialsSourceEntry] = new ListView[CredentialsSourceEntry]() {
    items = ObservableBuffer[CredentialsSourceEntry]()
  }

  private def makeCredentialsSourceDescription(): TextArea = new TextArea() {
    text = ""
    editable = false
    wrapText = true
  }

  val credentialsSourceListProperty: ObjectProperty[ObservableList[CredentialsSourceEntry]] = credentialsSourceList.items
  val credentialsSourceListSelectedProperty: ReadOnlyObjectProperty[CredentialsSourceEntry] = credentialsSourceList.selectionModel.value.selectedItemProperty()
  val credentialsSourceDescriptionProperty: StringProperty = credentialsSourceDescription.text
  
  val securityPoliciesListProperty: ObjectProperty[ObservableList[SecurityPolicyEntry]] = securityPoliciesList.items
  val securityPoliciesListSelectedProperty: ReadOnlyObjectProperty[SecurityPolicyEntry] = securityPoliciesList.selectionModel.value.selectedItemProperty()
  val securityPoliciesDescriptionProperty: StringProperty = securityPolicyDescription.text

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

  def fireSelectPolicy(position: Int): Unit = securityPoliciesList.selectionModel.value.select(position)
  def bindOnPolicyChange(listener: ChangeListener[SecurityPolicyEntry]) = securityPoliciesListSelectedProperty.addListener(listener)

  def fireSelectCredentialsSource(position: Int): Unit = credentialsSourceList.selectionModel.value.select(position)
  def bindOnCredentialsSourceChange(listener: ChangeListener[CredentialsSourceEntry]) = credentialsSourceListSelectedProperty.addListener(listener)

