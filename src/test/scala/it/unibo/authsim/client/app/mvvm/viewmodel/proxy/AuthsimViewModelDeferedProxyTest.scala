package it.unibo.authsim.client.app.mvvm.viewmodel.proxy

import it.unibo.authsim.client.app.mvvm.viewmodel.AuthsimViewModel
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.*

class AuthsimViewModelDeferedProxyTest extends AnyWordSpec with MockitoSugar:

  "Authsim view model defered proxy" when {

    "called without delegate" should {

      var proxy = new AuthsimViewModelDeferedProxy()
      var mockViewModel = MockitoSugar.mock[AuthsimViewModel]

      "saveUser" in {
        proxy.saveUser()

        verify(mockViewModel, times(0)).saveUser()
      }

      "generateUsers" in {
        proxy.generateUsers()

        verify(mockViewModel, times(0)).generateUsers()
      }

      "deleteSelectedUsers" in {
        proxy.deleteSelectedUsers()

        verify(mockViewModel, times(0)).deleteSelectedUsers()
      }

      "resetUsers" in {
        proxy.resetUsers()

        verify(mockViewModel, times(0)).resetUsers()
      }

      "launchAttack" in {
        proxy.launchAttack()

        verify(mockViewModel, times(0)).launchAttack()
      }

    }

    "called with delegate" should {

      var proxy = new AuthsimViewModelDeferedProxy()
      var mockViewModel = MockitoSugar.mock[AuthsimViewModel]
      proxy.delegate = Option(mockViewModel)

      "saveUser" in {
        proxy.saveUser()

        verify(mockViewModel, times(1)).saveUser()
      }

      "generateUsers" in {
        proxy.generateUsers()

        verify(mockViewModel, times(1)).generateUsers()
      }

      "deleteSelectedUsers" in {
        proxy.deleteSelectedUsers()

        verify(mockViewModel, times(1)).deleteSelectedUsers()
      }

      "resetUsers" in {
        proxy.resetUsers()

        verify(mockViewModel, times(1)).resetUsers()
      }

      "launchAttack" in {
        proxy.launchAttack()

        verify(mockViewModel, times(1)).launchAttack()
      }

    }

  }
