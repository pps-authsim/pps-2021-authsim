package it.unibo.authsim.library.dsl.policy.changers

import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import it.unibo.authsim.library.dsl.Protocol.Http
import it.unibo.authsim.library.dsl.policy.alphabet.PolicyAlphabet
import it.unibo.authsim.library.dsl.policy.builders.PolicyBuilder
import it.unibo.authsim.library.dsl.policy.defaults.stringpolicy.*
import it.unibo.authsim.library.dsl.policy.model.Policy
import it.unibo.authsim.library.dsl.policy.model.StringPolicies.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.*

import scala.language.postfixOps


class PolicyChangersTests extends AnyFunSuite:

  private val MAX_LENGTH: Int = 40
  private val MIN_UPPERCASE: Int = 2
  private val MIN_NUMBERS: Int = 5
  private val MIN_SYMBOLS: Int = 2
  private val newPasswordAlphabet: PolicyAlphabet = PolicyAlphabet.PolicyOTPAlphabet()
  private val newSaltAlphabet: PolicyAlphabet = PolicyAlphabet.PolicyDefaultAlphabet()

  private var policyImmutable: Policy = PolicyBuilder("fakebook") of UserIDPolicyDefault.HARD transmitWith Http() build;
  private var userIDPolicyImmutable: UserIDPolicy = UserIDPolicyDefault.SIMPLE
  private var passwordPolicyImmutable: PasswordPolicy = PasswordPolicyDefault.SIMPLE
  private var otpPolicyImmutable: OTPPolicy = OTPPolicyDefault.SIMPLE
  private var saltPolicyImmutable: SaltPolicy = SaltPolicyDefault.SIMPLE

  private def policyCanBeChanged(policyName: String, policyChangerMethod: String) = s"A $policyName can be changed with $policyChangerMethod"

  test(policyCanBeChanged("policy", "PolicyChanger")){
    policyImmutable = PolicyChanger(policyImmutable).of(UserIDPolicyDefault.MEDIUM).storeWith(RSA()).rebuild
    assert(
      policyImmutable.cryptographicAlgorithm == Some(RSA()) &&
        policyImmutable.credentialPolicies.contains(UserIDPolicyDefault.MEDIUM) &&
        !policyImmutable.credentialPolicies.contains(UserIDPolicyDefault.HARD)
    )
  }

  test(policyCanBeChanged("userID policy", "PolicyChanger.userID")){
    userIDPolicyImmutable = PolicyChanger.userID(userIDPolicyImmutable).minimumUpperChars(MIN_UPPERCASE).rebuild
    assert(userIDPolicyImmutable.minimumUpperChars == Some(MIN_UPPERCASE))
  }

  test(policyCanBeChanged("password policy", "PolicyChanger.password")){
    passwordPolicyImmutable = PolicyChanger.password(passwordPolicyImmutable).addAlphabet(newPasswordAlphabet).minimumNumbers(MIN_NUMBERS).rebuild
    assert(passwordPolicyImmutable.alphabet == newPasswordAlphabet &&  passwordPolicyImmutable.minimumNumbers == Some(MIN_NUMBERS))
  }

  test(policyCanBeChanged("opt policy", "PolicyChanger.otp")){
    otpPolicyImmutable = PolicyChanger.otp(otpPolicyImmutable).maximumLength(MAX_LENGTH).rebuild
    assert(otpPolicyImmutable.maximumLength == Some(MAX_LENGTH))
  }

  test(policyCanBeChanged("salt ", "PolicyChanger.salt")){
    saltPolicyImmutable = PolicyChanger.salt(saltPolicyImmutable).addAlphabet(newSaltAlphabet).minimumSymbols(MIN_SYMBOLS).rebuild
    assert(saltPolicyImmutable.alphabet == newSaltAlphabet &&  saltPolicyImmutable.minimumSymbols == Some(MIN_SYMBOLS))
  }