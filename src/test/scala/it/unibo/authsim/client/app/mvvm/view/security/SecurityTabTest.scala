package it.unibo.authsim.client.app.mvvm.view.security

import it.unibo.authsim.client.app.mvvm.view.{AuthsimViewSFX, AuthsimViewSFXTest}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.{UserEntry, UsersTab}
import javafx.event.ActionEvent
import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityPolicyEntry, SecurityTab}
import javafx.beans.value.ObservableValue
import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType

class SecurityTabTest extends AnyWordSpec :

    "Security Tab" when {

      AuthsimViewSFXTest.setUpViewTest()

      "security tab is constructed" should {

        val securityTab = new SecurityTab()

        "allow policy selection" in {
          val policy = new SecurityPolicyEntry("entry", "description")

          var capturedPolicy: SecurityPolicyEntry = null
          securityTab.bindOnPolicyChange((o: ObservableValue[_ <: SecurityPolicyEntry], oldValue: SecurityPolicyEntry, newValue: SecurityPolicyEntry) => capturedPolicy = newValue)

          securityTab.securityPoliciesListProperty.value.add(policy)

          securityTab.fireSelectPolicy(0)

          assert(policy.equals(capturedPolicy))
        }

        "allow credentials source selection" in {
          val source = new CredentialsSourceEntry(CredentialsSourceType.Sql, "description")

          var capturedSource: CredentialsSourceEntry = null
          securityTab.bindOnCredentialsSourceChange((o: ObservableValue[_ <: CredentialsSourceEntry], oldValue: CredentialsSourceEntry, newValue: CredentialsSourceEntry) => capturedSource = newValue)

          securityTab.credentialsSourceListProperty.value.add(source)

          securityTab.fireSelectCredentialsSource(0)

          assert(source.equals(capturedSource))
        }

      }

    }


