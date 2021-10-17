package it.unibo.authsim.client.app.mvvm.model

import it.unibo.authsim.client.app.mvvm.model.attack.AttackModel
import it.unibo.authsim.client.app.mvvm.model.security.SecurityModel
import it.unibo.authsim.client.app.mvvm.model.users.UsersModel
import org.scalatest.wordspec.AnyWordSpec
import it.unibo.authsim.client.app.mvvm.model.AuthsimModel

class AuthsimModelTest extends AnyWordSpec:

  "Authsim model" when {
      "Authsim model is constructed" should {

        val usersModel: UsersModel = new UsersModel()
        val securityModel: SecurityModel = new SecurityModel()
        val attackModel: AttackModel = new AttackModel()

        val model = new AuthsimModel(usersModel, securityModel, attackModel)

        "Lists be initialized as empty" in {
          val modelsNotNull = model.attackModel.attackSequenceList != null &&
          model.securityModel.securityPolicyList != null &&
          model.securityModel.credentialsSourceList != null &&
          model.usersModel.usersList != null

          assert(modelsNotNull)
        }

      }
  }
