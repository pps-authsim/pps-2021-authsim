package it.unibo.authsim.library.client.app.mvvm.viewmodel

import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimView
import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModel
import it.unibo.authsim.client.app.mvvm.model.attack.AttackModel
import it.unibo.authsim.client.app.mvvm.model.security.{SecurityModel, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackTab
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityTab}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.{UserEntry, UsersTab}
import javafx.embed.swing.JFXPanel
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import it.unibo.authsim.client.app.mvvm.model.users.User
import org.scalatest.BeforeAndAfterEach

import scala.collection.mutable.ListBuffer

object AuthsimViewModelTest:

  def setUpViewModelTest() =
    val jfxPanel = new JFXPanel

class AuthsimViewModelTest extends AnyWordSpec with Matchers with MockitoSugar with BeforeAndAfterEach:

  var mockModel: AuthsimModel = null
  var mockView: AuthsimView = null
  var viewModel: AuthsimViewModel = null

  override def beforeEach() =
    AuthsimViewModelTest.setUpViewModelTest()
    mockModel = new AuthsimModel(new UsersModel(), new SecurityModel(), new AttackModel())
    mockView = makeMockView()
    viewModel = new AuthsimViewModel(mockView, mockModel)

  "Authsim view model" when {

    "constructed" should {

      "have default user" in {
        assertUserTabHasDefaultValues()
      }

      "have default policy" in {
        assert(mockModel.securityModel.securityPolicyList.hasSameValues(SecurityPolicy.Default.all))
      }

      "have default credentials source" in {
        // TODO implement when credential sources are finalized
      }

      "have default attack sequence" in {
        // TODO implement when attack sequences are finalized
      }

    }

    "user is saved" should {

      "save user from view properties in model if user has username and password" in {
        mockView.usersTab.usernameProperty.value = "1234"
        mockView.usersTab.passwordProperty.value = "5678"

        viewModel.saveUser()

        assert(mockModel.usersModel.usersList.value.sameElements(Seq(new User("user", "password"), new User("1234", "5678"))))
        assert(mockView.usersTab.usersListProperty.value.get(0).equals(new UserEntry("user", "password")))
        assert(mockView.usersTab.usersListProperty.value.get(1).equals(new UserEntry("1234", "5678")))
      }

      "not save user from view properties in model if user has no username" in {
        mockView.usersTab.usernameProperty.value = ""
        mockView.usersTab.passwordProperty.value = "password"

        viewModel.saveUser()

        assertUserTabHasDefaultValues()
      }

      "not save user from view properties in model if user has no password" in {
        mockView.usersTab.usernameProperty.value = "user"
        mockView.usersTab.passwordProperty.value = ""

        viewModel.saveUser()

        assertUserTabHasDefaultValues()
      }

    }

    // TODO add users generated tests when ready

    "user is deleted" should {
      "have selected user deleted" in {
        mockView.usersTab.fireSelectUser(0)

        viewModel.deleteSelectedUsers()


        assert(mockModel.usersModel.usersList.value.isEmpty)
        assert(mockView.usersTab.usersListProperty.value.isEmpty)
      }

      "have user not deleted if no selection has been made" in {
        viewModel.deleteSelectedUsers()

        assertUserTabHasDefaultValues()
      }
    }

    "users are reset" should {

      "have no users in list" in {
        viewModel.resetUsers()

        assert(mockModel.usersModel.usersList.value.isEmpty)
        assert(mockView.usersTab.usersListProperty.value.isEmpty)
      }

    }

    "attack is launched" should {

      "launch attack with selected properties" in {
        mockView.securityTab.fireSelectPolicy(0)
        mockView.securityTab.fireSelectCredentialsSource(0)
        mockView.attackTab.fireSelectSequence(0)

        viewModel.launchAttack()

        val logValue = mockView.attackTab.attackLogProperty.value
        assert(logValue.equals(" [user password] SuperSimple source1 Sequence1"))
        // TODO changeme when library is hooked
      }

      "display error message with selected properties incomplete" in {
        mockView.securityTab.credentialsSourceListSelectedProperty

        viewModel.launchAttack()

        val logValue = mockView.attackTab.attackLogProperty.value
        assert(logValue.equals("Please, make sure to have at least one user, select a policy, a credentials source and an attack procedure before initiating an attack!"))
      }

    }

  }

  def makeMockView(): AuthsimView =
    val userTab = new UsersTab
    val securityTab = new SecurityTab
    val attackTab = new AttackTab

    val mock = MockitoSugar.mock[AuthsimView]
    doReturn(userTab).when(mock).usersTab
    doReturn(securityTab).when(mock).securityTab
    doReturn(attackTab).when(mock).attackTab
    mock

  def assertUserTabHasDefaultValues(): Unit =
    assert(mockModel.usersModel.usersList.value.sameElements(Seq(new User("user", "password"))))
    assert(mockView.usersTab.usersListProperty.value.get(0).equals(new UserEntry("user", "password")))
