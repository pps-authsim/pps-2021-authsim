package it.unibo.authsim.library.user

import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.DES
import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.cipher.symmetric.DESCipher
import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.RSACipher
import it.unibo.authsim.library.user.builder.{UserAutoBuilder, UserBuilder, UserCostumBuilder, UserInformationBuilder}
import it.unibo.authsim.library.dsl.policy.builders.StringPoliciesBuilders.UserIDPolicyBuilder
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.{CredentialPolicy, UserIDPolicy}
import it.unibo.authsim.library.user.builder.util.Util
import it.unibo.authsim.library.user.model.{User, UserInformation}
import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers

class UserInformationTest extends AnyFeatureSpec with GivenWhenThen with Matchers {

  private val min = 5
  private val shortName = Util.generateRandomString(min-1)
  private val name = Util.generateRandomString(min)
  private val password = Util.generateRandomString(min)
  private val admPassword = Util.generateRandomString(min)

  private val userIDPolicy: CredentialPolicy = UserIDPolicyBuilder() minimumLength min build

  private val autoUserBuilder = UserAutoBuilder() withPolicy (userIDPolicy)
  private val autoUser: User = autoUserBuilder.build

  private var costumUserBuilder = UserCostumBuilder() withName (name) withPassword (password) withPolicy (userIDPolicy)
  private val costumUser: Option[User] = costumUserBuilder.build

  feature ("UserInformation creation") {

    scenario("Password store in clear") {
      Given("No cryptographic algorithm")
      When("a userInformation is created")
      val userInformationBuilder: UserInformationBuilder = UserInformationBuilder() withUserName (costumUser.get.username) withPassword (password)
      val userInformation: Option[UserInformation] = userInformationBuilder.build

      Then("to check if the username is correctly saved")
      costumUser.get.username shouldBe userInformation.get.username

      And("to check if the password is stored in clear")
      userInformation.get.algorithm shouldBe (None)

      And("to check if the password is correctly saved")
      costumUser.get.password shouldBe userInformation.get.password
    }

    scenario("Hashed password") {
      Given("an hash algorithm")
      val sha = HashFunction.SHA384()

      When("a user information is created with an hashed password")
      val userInformationBuilder: UserInformationBuilder = UserInformationBuilder() withUserName (autoUser.username) withPassword (sha.hash(autoUser.password)) withAlgorithm (sha)
      val userInformation: Option[UserInformation] = userInformationBuilder.build

      Then("to check if the username is correctly saved")
      autoUser.username shouldBe userInformation.get.username

      And("to check if the password is stored in clear")
      userInformation.get.algorithm.get shouldBe sha

      And("to check if the password was correctly saved")
      sha.hash(autoUser.password) shouldBe userInformation.get.password
    }

    scenario("Password encrypted with a symmetric algorithm") {
      Given("a symmetric algorithm")
      val des = DES()

      And("a cipher for the algorithm")
      val cipher = DESCipher()

      When("a user information is created with a password encrypted")
      val userInformationBuilder: UserInformationBuilder = UserInformationBuilder() withUserName (autoUser.username) withPassword (cipher.encrypt(autoUser.password, admPassword)) withAlgorithm (des)
      val userInformation: Option[UserInformation] = userInformationBuilder.build

      Then("to check if the username is correctly saved")
      autoUser.username shouldBe userInformation.get.username

      And("to check if the password is stored in clear")
      userInformation.get.algorithm.get shouldBe des

      And("to check if the password is correctly saved")
      autoUser.password shouldBe cipher.decrypt(userInformation.get.password, admPassword)
    }

    scenario("Password encrypted with a asymmetric algorithm") {
      Given("a asymmetric algorithm")
      val rsa = RSA()

      And("a cipher for the algorithm")
      val cipher = RSACipher()
      cipher.generateKeys()

      info("A new key pair should be generated")
      val keypair= cipher.generateKeys("test.ser")
      val(pvt, pub) = (keypair.privateKey, keypair.publicKey)

      When("a user information is created with a password encrypted")
      val userInformationBuilder: UserInformationBuilder = UserInformationBuilder() withUserName (autoUser.username) withPassword (cipher.encrypt(autoUser.password, pvt)) withAlgorithm (rsa)
      val userInformation: Option[UserInformation] = userInformationBuilder.build

      Then("to check if the username is correctly saved")
      autoUser.username shouldBe userInformation.get.username

      And("to check if the password is stored in clear")
      userInformation.get.algorithm.get shouldBe rsa

      And("to check if the password is correctly saved")
      autoUser.password shouldBe cipher.decrypt(userInformation.get.password, pub)
    }

  }
}

