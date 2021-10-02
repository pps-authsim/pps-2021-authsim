package it.unibo.authsim.client.app.mvvm.view.attack

import it.unibo.authsim.client.app.mvvm.view.AuthsimViewSFXTest
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.{AttackSequenceEntry, AttackTab}
import it.unibo.authsim.client.app.mvvm.view.tabs.security.SecurityPolicyEntry
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import org.scalatest.wordspec.AnyWordSpec

class AttackTabTest extends AnyWordSpec :

  "Attack Tab" when {

    AuthsimViewSFXTest.setUpViewTest()

    "attack tab is constructed" should {

      val attackTab = new AttackTab()

      "allow sequence selection" in {
        val sequence = new AttackSequenceEntry("attack", "description")

        var capturedSequence: AttackSequenceEntry = null
        attackTab.bindOnSequenceChange((o: ObservableValue[_ <: AttackSequenceEntry], oldValue: AttackSequenceEntry, newValue: AttackSequenceEntry) => capturedSequence = newValue)

        attackTab.attackSequenceListProperty.value.add(sequence)

        attackTab.fireSelectSequence(0)

        assert(sequence.equals(capturedSequence))
      }

      "allow attack launch" in {
        val outputText = "Test"

        attackTab.bindOnAttackLaunch((e: ActionEvent) => attackTab.attackLogProperty.value = outputText)
        attackTab.fireAttackLaunch()

        assert(outputText.equals(attackTab.attackLogProperty.value))
      }

    }

  }
