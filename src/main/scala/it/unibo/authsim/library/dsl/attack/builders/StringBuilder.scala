package it.unibo.authsim.library.dsl.attack.builders

/**
 * Utility class which allows to generate a list of strings given an alphabet.
 * The size of the alphabet has more influence on the execution time rather than the length of the requested string,
 * so beware of big alphabets, they are likely to OOM-kill the execution.
 */
class StringBuilder(private val alphabet: List[String]) {
  /**
   * Generate all the strings with a specific length
   * @param length The length of the strings
   * @return A lazy list with the generated strings
   */
  def getAllStringsWithLength(length: Int): LazyList[String] = length match {
    case 0 => LazyList("")
    case 1 => LazyList.from(alphabet.toArray)
    case _ => getAllStringsWithLength(length - 1).flatMap(s => alphabet.map(symbol => s + symbol))
  }

  /**
   * Get all the strings long up to the input length.
   * @param maxLength The maximum length of a string
   * @return The lazy list with all the strings generated
   */
  def getAllStringsUpToLength(maxLength: Int): LazyList[String] = maxLength match {
    case 0 | 1 => getAllStringsWithLength(maxLength)
    case _ => LazyList.from(alphabet.toArray) ++ getAllStringsUpToLength(maxLength - 1).flatMap(s => alphabet.map(symbol => s + symbol))
  }
}
