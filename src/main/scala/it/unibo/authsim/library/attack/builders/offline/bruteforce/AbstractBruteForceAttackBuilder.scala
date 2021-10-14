package it.unibo.authsim.library.attack.builders.offline.bruteforce

import it.unibo.authsim.library.UserProvider
import it.unibo.authsim.library.alphabet.Alphabet
import it.unibo.authsim.library.attack.builders.offline.{OfflineAttack, OfflineAttackBuilder}
import it.unibo.authsim.library.attack.builders.{Attack, ConcurrentStringCombinator}
import it.unibo.authsim.library.attack.statistics.Statistics
import it.unibo.authsim.library.consumers.StatisticsConsumer
import it.unibo.authsim.library.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.cryptography.algorithm.asymmetric.RSA
import it.unibo.authsim.library.cryptography.algorithm.symmetric.{AES, Caesar, DES}
import it.unibo.authsim.library.cryptography.cipher.symmetric.{AESCipher, CaesarCipher, DESCipher}
import it.unibo.authsim.library.cryptography.cipher.asymmetric.RSACipher
import it.unibo.authsim.library.user.model.{User, UserInformation}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, NANOSECONDS}
import scala.concurrent.{Await, Future, TimeoutException}

/**
 * A builder of bruteforce attacks. It allows to specify the Alphabet to use in the attack and maximum length of the string to build.
 * The alphabet is mandatory.
 * The length of the words defaults to 1.
 */
abstract class AbstractBruteForceAttackBuilder[T <: Alphabet[T]] extends OfflineAttackBuilder:
  private var alphabet: Alphabet[T] = null
  private var _maximumLength = 1

  /**
   * Sets the alphabet to use for the attack.
   *
   * A bare bruteforce attack should have an alphabet with only 1-length symbols,
   * while other attacks can use a different alphabet
   * (for example, a dictionary attack uses the dictionary itself as the alphabet).
   * @param alphabet The alphabet to use.
   * @return The builder.
   */
  protected def protectedUsingAlphabet(alphabet: Alphabet[T]): this.type = this.builderMethod[Alphabet[T]](alphabet => this.alphabet = alphabet)(alphabet)

  /**
   * @return The used alphabet.
   */
  protected def protectedGetAlphabet: Alphabet[T] = this.alphabet

  /**
   * Sets the maximum number of combinations of symbols from the alphabet.
   * Defaults to 1 (which means the attack only uses the provided symbols, without combining them).
   * @param maximumLength The maximum combination number.
   * @return The builder.
   */
  protected def protectedMaximumLength(maximumLength: Int): this.type = this.builderMethod[Int](l => this._maximumLength = l)(maximumLength)

  /**
   * @return The maximum combination number.
   */
  protected def protectedGetMaximumLength: Int = this._maximumLength

  final override def build: Attack =
    require(this.getTarget() != null, "UserProvider not set. Aborting attack creation.")
    require(this.protectedGetAlphabet != null, "Alphabet not set. Aborting attack creation.")
    new BruteForceAttack(this.getTarget(), this.protectedGetAlphabet, this.protectedGetMaximumLength, this.getStatisticsConsumer(), this.getTimeout(), this.getNumberOfWorkers)

private class BruteForceAttack(private val target: UserProvider, private val alphabet: Alphabet[_], private val maximumLength: Int, private val logTo: Option[StatisticsConsumer], private val timeout: Option[Duration], private val jobs: Int) extends OfflineAttack:

  private var timedOut: Boolean = false

  override def start(): Unit =
    var jobResults: List[Future[Statistics]] = List.empty
    var totalResults = Statistics.zero
    val monitor = new ConcurrentStringCombinator(alphabet, maximumLength)
    val startTime = System.nanoTime()
    (1 to jobs).foreach(_ => jobResults = Future(futureJob(target.userInformations(), monitor)) :: jobResults)
    try {
      Await.result(Future.sequence(jobResults), timeout.getOrElse(Duration.Inf)).foreach(stats => totalResults = totalResults + stats)
    } catch {
      case e: TimeoutException => {
        this.timedOut = true
        totalResults = totalResults + Statistics.timedOut
      }
    }
    val endTime = System.nanoTime()
    val elapsedTime = Duration(endTime - startTime, NANOSECONDS)
    totalResults = totalResults + Statistics.onlyElapsedTime(elapsedTime)
    logTo.foreach(logSpec => logSpec.consume(totalResults))

  private def futureJob(targetUsers: List[UserInformation], stringProvider: ConcurrentStringCombinator): Statistics =
    var localStatistics = Statistics.zero
    var nextPassword = stringProvider.getNextString()
    while !nextPassword.isEmpty && !this.timedOut do
      val nextPasswordString = nextPassword.get
      targetUsers.foreach(user => {
        val hashedPassword = retrievePasswordEncrypter(user)(nextPasswordString)
        localStatistics = localStatistics + Statistics.onlyBreaches(hashedPassword == user.password match {
          case true => Set(User(user.username, nextPasswordString))
          case false => Set()
        })
      })
      localStatistics = localStatistics + Statistics.onlyAttempts(attempts = 1)
      nextPassword = stringProvider.getNextString()
    end while
    localStatistics

  private def retrievePasswordEncrypter(userInfo: UserInformation): String => String =
    userInfo.algorithm match {
      case None => s => s
      case Some(algorithm) => algorithm match {
        case hashFunction: HashFunction => hashFunction.hash
        case caesar: Caesar => s => CaesarCipher().encrypt(s, caesar.keyLength)
        case des: DES => s => DESCipher().encrypt(s, algorithm.salt.getOrElse(""))
        case aes: AES => s => AESCipher().encrypt(s, algorithm.salt.getOrElse(""))
        case rsa: RSA => s => RSACipher().encrypt(s, algorithm.salt.getOrElse(""))
      }
    }