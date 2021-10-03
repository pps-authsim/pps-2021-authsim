package it.unibo.authsim.client.app.mvvm.view

import it.unibo.authsim.client.app.mvvm.view.AuthsimViewSFX
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackTab
import it.unibo.authsim.client.app.mvvm.view.tabs.security.SecurityTab
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UsersTab
import org.scalatest.wordspec.AnyWordSpec
import javafx.embed.swing.JFXPanel
import scalafx.application.JFXApp3

object AuthsimViewSFXTest:
  def setUpViewTest() =
    val jfxPanel = new JFXPanel

class AuthsimViewSFXTest extends AnyWordSpec:

  "Autsim view" when {

    AuthsimViewSFXTest.setUpViewTest()

    "number text field is constructed" should {

      "allow number input" in {
        val numberInput = AuthsimViewSFX.makeNumberTextField()

        numberInput.text = "123"

        val text = numberInput.text.value
        assert(text == "123")
      }

      "disallow text input" in {
        val numberInput = AuthsimViewSFX.makeNumberTextField()

        numberInput.text = "abc"

        val text = numberInput.text.value
        assert(text == "")
      }

    }

  }
