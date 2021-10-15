package it.unibo.authsim.client.app.simulation.attacks

import it.unibo.authsim.library.UserProvider
import it.unibo.authsim.library.alphabet.{Dictionary, SymbolicAlphabet}
import it.unibo.authsim.library.attack.builders.{AttackBuilder, ConcurrentStringCombinator}
import it.unibo.authsim.library.attack.builders.offline.bruteforce.{BruteForceAttackBuilder, DictionaryAttackBuilder}
import it.unibo.authsim.library.consumers.StatisticsConsumer
import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.policy.alphabet.PolicyAlphabet.PolicyDefaultAlphabet

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration

class AttacksFactory(private val userProvider: UserProvider, private val logger: StatisticsConsumer):

  private val defaultAlphabets = new PolicyDefaultAlphabet()
  /**
   * @return an AttackBuilder configured to attack a userProvider, using lowercase characters to construct the password string
   *         with a maximum length of 6 and logging to the given logger, without timeout.
   */
  def bruteForceLowers(): AttackBuilder = new BruteForceAttackBuilder() against userProvider usingAlphabet defaultAlphabets.lowers logTo logger maximumWordLength 6 jobs (sys.runtime.availableProcessors() - 2)

  /**
   * @return an AttackBuilder configured to attack a userProvider, using both lower and upper case characters to construct the password string
   *         with a maximum length of 10 and logging to the given logger, with a timeout of 120 seconds (2 minutes).
   */
  def bruteForceLetters(): AttackBuilder = new BruteForceAttackBuilder() against userProvider usingAlphabet (defaultAlphabets.lowers and defaultAlphabets.uppers) maximumWordLength 10 jobs (sys.runtime.availableProcessors() - 2) logTo logger timeout Duration(120, TimeUnit.SECONDS)

  /**
   * @return an AttackBuilder configured to attack a userProvider, using all alphanumeric and symbols characters to construct the password string
   *         with a maximum length of 16 and logging to the given logger, with a timeout of 600 seconds (10 minutes).
   */
  def bruteForceAll(): AttackBuilder = new BruteForceAttackBuilder() against userProvider usingAlphabet defaultAlphabets.alphanumericsymbols maximumWordLength 16 jobs sys.runtime.availableProcessors() logTo logger timeout Duration(600, TimeUnit.SECONDS)

  /**
   * @return a DictionaryAttackBuilder configured to attack a userProvider, with the dictionary of the top 97 most common passwords, combining them up to 3 times,
   *         logging to logger and with a timeout of 120 seconds (2 minutes).
   */
  def dictionaryMostCommonPasswords(): AttackBuilder = new DictionaryAttackBuilder() against userProvider withDictionary Dictionary(top97MostCommonPasswords) maximumCombinedWords 3 jobs (sys.runtime.availableProcessors() - 2) logTo logger timeout Duration(120, TimeUnit.SECONDS)

  /**
   *
   * @return a DictionaryAttackBuilder configured to guess only "password" logging to logger and with no timeout
   */
  def guessDefaultPassword(): AttackBuilder = new DictionaryAttackBuilder against userProvider withDictionary Dictionary(Set("password")) maximumCombinedWords 1 logTo logger

  private val top97MostCommonPasswords =
    Set(("123456"), ("password"), ("12345678"), ("qwerty"), ("123456789"), ("12345"), ("1234"), ("111111"), ("1234567"), ("dragon"),
      ("123123"), ("wrongpassword"), ("abc123"), ("football"), ("monkey"), ("letmein"), ("696969"), ("shadow"), ("master"), ("666666"),
      ("qwertyuiop"), ("123321"), ("mustang"), ("1234567890"), ("michael"), ("654321"), ("superman"), ("1qaz2wsx"), ("7777777"), ("abcdef"),
      ("121212"), ("000000"), ("qazwsx"), ("123qwe"), ("killer"), ("trustno1"), ("jordan"), ("jennifer"), ("zxcvbnm"), ("asdfgh"), ("hunter"),
      ("buster"), ("soccer"), ("harley"), ("batman"), ("andrew"), ("tigger"), ("sunshine"), ("iloveyou"), ("mycomeputer"), ("2000"), ("charlie"),
      ("robert"), ("thomas"), ("hockey"), ("ranger"), ("daniel"), ("starwars"), ("klaster"), ("112233"), ("george"), ("computer"), ("michelle"),
      ("jessica"), ("pepper"), ("easy"), ("zxcvbn"), ("555555"), ("11111111"), ("131313"), ("freedom"), ("777777"), ("pass"), ("maggie"),
      ("159753"), ("aaaaaa"), ("ginger"), ("princess"), ("joshua"), ("cheese"), ("amanda"), ("summer"), ("love"), ("ashley"), ("6969"),
      ("nicole"), ("chelsea"), ("biteme"), ("matthew"), ("access"), ("yankees"), ("987654321"), ("dallas"), ("austin"), ("thunder"),
      ("taylor"), ("matrix"), ("william"), ("corvette"), ("hello")
    )
