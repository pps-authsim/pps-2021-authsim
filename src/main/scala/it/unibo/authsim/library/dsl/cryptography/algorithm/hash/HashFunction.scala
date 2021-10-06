package it.unibo.authsim.library.dsl.cryptography.algorithm.hash

import com.google.common.hash.Hashing
import com.google.common.io.BaseEncoding
import it.unibo.authsim.library.dsl.cryptography.algorithm.CryptographicAlgorithm
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

import java.nio.charset.StandardCharsets

/**
 * Trait representing the hash function
 */
trait HashFunction extends CryptographicAlgorithm:
  /**
   * Method that perform the hash conversion: it converts the input value into a string created according
   * to the hash algorithm chosen for the conversion.
   *
   * @param value             input value to transfrom
   * @tparam A                type value of the input
   * @return                  a string representing the hashed input value
   */
  def hash[A](value: A): String

  /**
   * Setter for the salt value
   *
   * @param salt              a new salt to use during the hash transformation
   * @tparam A                type value of the input
   */
  def salt_[A](salt:A): Unit


/**
 * Companion object of the HashFunction trait
 */
object HashFunction:
  import it.unibo.authsim.library.dsl.cryptography.util.ImplicitConversion._

  /**
   *  Class representing a basic implementation of the HashFunction algorithm
   */
  abstract class BasicHash extends HashFunction:
    /**
     * Protected value representing the salt
     */
    protected var _salt: String= ""

    /**
     * Getter for the salt value
     *  @return                        an option of the salt value, or none if ot is not set
     */
    override def salt: Option[String]= Option(_salt)

    /**
     * Setter for the salt value
     *
     * @param salt              a new salt to use during the hash transformation
     * @tparam A                type value of the input
     */
    override def salt_[A](salt:A): Unit= _salt = salt

    /**
     * Getter for the name of the Hash algorithm
     *
     *  @return                 a string representing the name of crypthographic algorithm
     */
    override def algorithmName: String = this.toString

  /**
   * Class performing the SHA1 algorithm
   */
  case class SHA1() extends BasicHash:
    /**
     * Method perfoming the hash transformation according to the SHA1 algorithm
     *
     * @param password          value to be converted
     * @tparam A                type value of the input
     *  @return                  a string representing the hashed input value
     */
    override def hash[A](password: A): String = DigestUtils.shaHex(password.concat(salt.get).getBytes(StandardCharsets.UTF_8))

  /**
   * Class performing the SHA256 algorithm
   */
  case class SHA256() extends BasicHash:
    /**
     * Method perfoming the hash transformation according to the SHA256 algorithm
     *
     * @param password          value to be converted
     * @tparam A                type value of the input
     *  @return                  a string representing the hashed input value
     */
    override def hash[A](password: A): String = Hashing.sha256().hashString(password.concat(_salt), StandardCharsets.UTF_8).toString

  /**
   * Class performing the SHA384 algorithm
   */
  case class SHA384() extends BasicHash:
    /**
     * Method perfoming the hash transformation  according to the SHA384 algorithm
     *
     * @param password          value to be converted
     * @tparam A                type value of the input
     *  @return                  a string representing the hashed input value
     */
    override def hash[A](password: A): String = Hashing.sha384().hashString(_salt.concat(password), StandardCharsets.UTF_8).toString

  /**
   * Class performing the MD5 algorithm
   */
  case class MD5() extends BasicHash:
    /**
     * Method perfoming the hash transformation  according to the MD5 algorithm
     *
     * @param password          value to be converted
     * @tparam A                type value of the input
     *  @return                  a string representing the hashed input value
     */
    override def hash[A](password: A): String = DigestUtils.md5Hex(_salt.concat(password).getBytes(StandardCharsets.UTF_8))