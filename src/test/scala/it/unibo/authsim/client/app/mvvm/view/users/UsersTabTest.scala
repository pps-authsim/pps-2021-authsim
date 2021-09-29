package it.unibo.authsim.client.app.mvvm.view.users

import it.unibo.authsim.client.app.mvvm.view.{AuthsimView, AuthsimViewTest}
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackTab
import it.unibo.authsim.client.app.mvvm.view.tabs.users.{UserEntry, UsersTab}
import javafx.collections.ObservableList
import javafx.embed.swing.JFXPanel
import javafx.event.ActionEvent
import javafx.scene.input.KeyEvent
import org.scalatest.wordspec.AnyWordSpec
import scalafx.application.JFXApp3
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.input.KeyCode

class UsersTabTest extends AnyWordSpec :

  "Users tab" when {

    AuthsimViewTest.setUpViewTest()

    "user tab is constructed" should {

      val usersTab = new UsersTab()

      "allow user creation" in {
        var capturedUser: UserEntry = null
        usersTab.bindOnSave((e: ActionEvent) => capturedUser = new UserEntry(usersTab.usernameProperty.value, usersTab.passwordProperty.value))

        usersTab.usernameProperty.value = "user"
        usersTab.passwordProperty.value = "pass"

        usersTab.fireSave()

        val expectedUser: UserEntry = new UserEntry("user", "pass")
        assert(expectedUser.equals(capturedUser))
      }

      "allow user generation" in {
        // TODO implement when ready
      }

      "allow deleting selected user in" in {
        val user1 = new UserEntry("user1", "pass")
        val user2 = new UserEntry("user2", "pass")

        usersTab.usersListProperty.value.add(user1)
        usersTab.usersListProperty.value.add(user2)

        usersTab.fireSelectUser(0)

        usersTab.bindOnDeleteSelected((e: ActionEvent) => usersTab.usersListProperty.value.remove(usersTab.usersListSelectedProperty.get()))

        usersTab.fireDelete()

        assert(usersTab.usersListProperty.value.size() == 1)
        assert(usersTab.usersListProperty.value.get(0).equals(user2))
      }

      "allow clearing all users in" in {
        val user1 = new UserEntry("user1", "pass")
        val user2 = new UserEntry("user2", "pass")

        usersTab.usersListProperty.value.add(user1)
        usersTab.usersListProperty.value.add(user2)

        usersTab.fireSelectUser(0)

        usersTab.bindOnReset((e: ActionEvent) => usersTab.usersListProperty.value.clear())

        usersTab.fireReset()

        assert(usersTab.usersListProperty.value.size() == 0)
      }

    }

  }


