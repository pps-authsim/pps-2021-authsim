package it.unibo.authsim.client.app.mvvm.model

import it.unibo.authsim.client.app.mvvm.model.attack.AttackModel
import it.unibo.authsim.client.app.mvvm.model.security.SecurityModel
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import it.unibo.authsim.client.app.mvvm.view.tabs.users.UserEntry
import javafx.collections.ObservableList

object AuthsimModel:
  def bindPropertiesWithObservableList[A, B](observableListBuffer: ObservableListBuffer[A], propertiesList: ObservableList[B], mapper: (A => B)) = {
    observableListBuffer.onAdd = Option(o => propertiesList.add(mapper.apply(o)))
    observableListBuffer.onRemove = Option(o => propertiesList.remove(mapper.apply(o)))
  }

class AuthsimModel(val usersModel: UsersModel, val securityModel: SecurityModel, val attackModel: AttackModel)
