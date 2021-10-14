package it.unibo.authsim.client.app.mvvm.viewmodel

import it.unibo.authsim.client.app.mvvm.binder.{ModelInitializer, ViewPropertiesBinder}
import it.unibo.authsim.client.app.mvvm.common.CredentialsSourceType
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel
import it.unibo.authsim.client.app.mvvm.view.AuthsimViewSFX
import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModelSFX
import it.unibo.authsim.client.app.mvvm.model.attack.AttackModel
import it.unibo.authsim.client.app.mvvm.model.security.{CredentialsSource, SecurityModel, SecurityPolicy}
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.client.app.mvvm.view.tabs.attack.AttackTab
import it.unibo.authsim.client.app.mvvm.view.tabs.security.{CredentialsSourceEntry, SecurityTab}
import it.unibo.authsim.client.app.mvvm.view.tabs.users.{UserEntry, UserGenerationPreset, UsersTab}
import it.unibo.authsim.client.app.mvvm.viewmodel.attack.AttackViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.proxy.AuthsimViewModelDeferedProxy
import it.unibo.authsim.client.app.mvvm.viewmodel.security.SecurityViewModel
import it.unibo.authsim.client.app.mvvm.viewmodel.users.UsersViewModel
import javafx.embed.swing.JFXPanel
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.any
import it.unibo.authsim.library.user.model.User
import org.scalatest.BeforeAndAfterEach

import scala.collection.mutable.ListBuffer
import it.unibo.authsim.client.app.components.registry.ComponentRegistry.SimulationRunner

object AuthsimViewModelSFXIntegrationTest:

  def setUpViewModelTest() =
    val jfxPanel = new JFXPanel

class AuthsimViewModelSFXIntegrationTest extends AnyWordSpec with Matchers with MockitoSugar with BeforeAndAfterEach:

  var model: AuthsimModel = null
  var mockView: AuthsimViewSFX = null
  var viewModel: AuthsimViewModel = null
  var mockRunner: SimulationRunner = null

  override def beforeEach() =
    AuthsimViewModelSFXIntegrationTest.setUpViewModelTest()
    model = new AuthsimModel(new UsersModel(), new SecurityModel(), new AttackModel())
    mockView = makeMockView()
    mockRunner = makeMockRunner()

    val viewModelDeferedProxy = new AuthsimViewModelDeferedProxy

    val usersViewModel: UsersViewModel = ViewPropertiesBinder.bindUsersTab(mockView, viewModelDeferedProxy)
    val securityViewModel: SecurityViewModel = ViewPropertiesBinder.bindSecurityTab(mockView, viewModelDeferedProxy)
    val attackViewModel: AttackViewModel = ViewPropertiesBinder.bindAttackTab(mockView, viewModelDeferedProxy)

    viewModel = new AuthsimViewModelSFX(usersViewModel, securityViewModel, attackViewModel, model, mockRunner)

    ModelInitializer.initializeUsersModel(model.usersModel)
    ModelInitializer.initializeSecurityModel(model.securityModel)
    ModelInitializer.initializeAttackModel(model.attackModel)

    viewModelDeferedProxy.delegate = Option(viewModel)

  "Authsim view model" when {

    "constructed" should {

      "have default user" in {
        assertUserTabHasDefaultValues()
      }

      "have default policy" in {
        assert(model.securityModel.securityPolicyList.hasSameValues(SecurityPolicy.Default.all))
      }

      "have default credentials source" in {
        val sqlSource = CredentialsSourceType.Sql
        val sqlSourceDescription = CredentialsSourceType.Sql.description
        val mongoSource = CredentialsSourceType.Mongo
        val mongoSourceDescription = CredentialsSourceType.Mongo.description

        assert(model.securityModel.credentialsSourceList.hasSameValues(List(new CredentialsSource(sqlSource, sqlSourceDescription), new CredentialsSource(mongoSource, mongoSourceDescription))))
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

        assert(model.usersModel.usersList.value.sameElements(Seq(User("user", "password"), User("1234", "5678"))))
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

    "users are generated" should {
      "generate expected quantity of users" in {
        mockView.usersTab.presetProperty.value = new UserGenerationPreset("Simple", "")
        mockView.usersTab.quantityProperty.value = "3"

        viewModel.generateUsers()

        assert(model.usersModel.usersList.value.length == 4)
        assert(mockView.usersTab.usersListProperty.value.size == 4)
      }
    }

    "user is deleted" should {
      "have selected user deleted" in {
        mockView.usersTab.fireSelectUser(0)

        viewModel.deleteSelectedUsers()


        assert(model.usersModel.usersList.value.isEmpty)
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

        assert(model.usersModel.usersList.value.isEmpty)
        assert(mockView.usersTab.usersListProperty.value.isEmpty)
      }

    }

    "attack is launched" should {

      "launch attack if necessary properties are selected" in {
        mockView.securityTab.fireSelectPolicy(0)
        mockView.securityTab.fireSelectCredentialsSource(0)
        mockView.attackTab.fireSelectSequence(0)

        viewModel.launchAttack()

        Mockito.verify(mockRunner).runSimulation(any())
      }

      "display error message with selected properties incomplete" in {
        mockView.securityTab.credentialsSourceListSelectedProperty

        viewModel.launchAttack()

        Mockito.verify(mockRunner, times(0)).runSimulation(any())
      }

    }

  }

  private def makeMockView(): AuthsimViewSFX =
    val userTab = new UsersTab
    val securityTab = new SecurityTab
    val attackTab = new AttackTab

    val mock = MockitoSugar.mock[AuthsimViewSFX]
    doReturn(userTab).when(mock).usersTab
    doReturn(securityTab).when(mock).securityTab
    doReturn(attackTab).when(mock).attackTab
    mock

  private def assertUserTabHasDefaultValues(): Unit =
    assert(model.usersModel.usersList.value.sameElements(Seq(User("user", "password"))))
    assert(mockView.usersTab.usersListProperty.value.get(0).equals(new UserEntry("user", "password")))

  private def makeMockRunner(): SimulationRunner =
    MockitoSugar.mock[SimulationRunner]