package it.unibo.authsim.library.dsl.attack.builders.offline.bruteforce

import it.unibo.authsim.library.dsl.alphabet.Alphabet
import it.unibo.authsim.library.dsl.attack.builders.offline.{OfflineAttack, OfflineAttackBuilder}
import it.unibo.authsim.library.dsl.attack.builders.{Attack, ConcurrentStringCombinator}
import it.unibo.authsim.library.dsl.attack.statistics.Statistics
import it.unibo.authsim.library.dsl.consumers.StatisticsConsumer
import it.unibo.authsim.library.dsl.UserProvider
import it.unibo.authsim.library.dsl.cryptography.algorithm.hash.HashFunction
import it.unibo.authsim.library.dsl.cryptography.algorithm.asymmetric.RSA
import it.unibo.authsim.library.dsl.cryptography.algorithm.symmetric.{AES, DES, Caesar}
import it.unibo.authsim.library.dsl.cryptography.cipher.asymmetric.RSACipher
import it.unibo.authsim.library.dsl.cryptography.cipher.symmetric.{AESCipher, CaesarCipher, DESCipher}
import it.unibo.authsim.library.user.model.{User, UserInformation}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, Future, TimeoutException}

/**
 * A builder of bruteforce attacks.
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

  final override def build: Attack = new BruteForceAttack(this.getTarget(), this.getHashFunction(), this.protectedGetAlphabet, this.protectedGetMaximumLength, this.getStatisticsConsumer(), this.getTimeout(), this.getNumberOfWorkers)

private class BruteForceAttack(private val target: UserProvider, private val hashFunction: HashFunction, private val alphabet: Alphabet[_], private val maximumLength: Int, private val logTo: Option[StatisticsConsumer], private val timeout: Option[Duration], private val jobs: Int) extends OfflineAttack:

  override def start(): Unit =
    var jobResults: List[Future[Statistics]] = List.empty
    var totalResults = Statistics.zero
    val monitor = new ConcurrentStringCombinator(alphabet, maximumLength)
    val startTime = System.nanoTime()
    (1 to jobs).foreach(_ => jobResults = Future(futureJob(target.userInformations(), monitor)) :: jobResults)
    try {
      Await.result(Future.sequence(jobResults), timeout.getOrElse(Duration.Inf)).foreach(stats => totalResults = totalResults + stats)
    } catch {
      case e: TimeoutException => totalResults = totalResults + Statistics.timedOut
    }
    val endTime = System.nanoTime()
    val elapsedTime = Duration(endTime - startTime, MILLISECONDS)
    totalResults = totalResults + Statistics.onlyElapsedTime(elapsedTime)
    logTo.foreach(logSpec => logSpec.consume(totalResults))

  private def futureJob(targetUsers: List[UserInformation], stringProvider: ConcurrentStringCombinator): Statistics =
    var localStatistics = Statistics.zero
    var nextPassword = stringProvider.getNextString()
    while !nextPassword.isEmpty do
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