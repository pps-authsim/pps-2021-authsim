package it.unibo.authsim.client.app.simulation.provider

import it.unibo.authsim.client.app.components.persistence.{UserEntity, UserRepository}
import it.unibo.authsim.client.app.simulation.exception.SimulationException
import it.unibo.authsim.library.dsl.UserProvider
import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm
import it.unibo.authsim.library.user.model.{CryptoInformation, UserInformation}

import scala.util.{Failure, Success}

class RepositoryUserProvider(private val userRepository: UserRepository, private val algorithm: Option[CryptographicAlgorithm]) extends UserProvider:

  def userInformations(): List[UserInformation] =
    userRepository.retrieveAllUsers() match
      case Success(users) => users.map(enrichUserEntityWithCryptyInformation).toList
      case Failure(error) => throw new SimulationException("Could not retrieve user information")

  private val enrichUserEntityWithCryptyInformation: (UserEntity => UserInformation) =
    (userEntity: UserEntity) => UserInformation(userEntity.username, userEntity.password, algorithm)
