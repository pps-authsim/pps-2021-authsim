package it.unibo.authsim.library.client.app.mvvm.view

import it.unibo.authsim.client.app.mvvm.view.AuthsimView
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackTab
import it.unibo.authsim.client.app.mvvm.view.tabs.security.SecurityTab
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import org.scalatest.wordspec.AnyWordSpec
import javafx.embed.swing.JFXPanel

object AuthsimViewTest:
  def setUpViewTest() =
    val jfxPanel = new JFXPanel

class AuthsimViewTest extends AnyWordSpec:

  "Autsim view" when {

    AuthsimViewTest.setUpViewTest()

    "number text field is constructed" should {

      "allow number input" in {
        val numberInput = AuthsimView.makeNumberTextField()

        numberInput.text = "123"

        val text = numberInput.text.value
        assert(text == "123")
      }

      "disallow text input" in {
        val numberInput = AuthsimView.makeNumberTextField()

        numberInput.text = "abc"

        val text = numberInput.text.value
        assert(text == "")
      }

    }

    "view is constructed" should {

      // TODO instancing view in test causes nullpointer exception in stage title set

    }

  }
