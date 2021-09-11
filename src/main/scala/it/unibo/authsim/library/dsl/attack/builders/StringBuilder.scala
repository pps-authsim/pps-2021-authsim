package it.unibo.authsim.library.dsl.attack.builders

/**
 * A ConcurrentStringProvider lazily generates all the possible string given an alphabet and a maximum length.
 * It also ensure mutual exclusion for retrieving the next string.
 *
 * @param alphabet The alphabet to use to generate the strings
 * @param maxLength The maximum length of the strings to generate
 */
class ConcurrentStringProvider(val alphabet: List[String], val maxLength: Int) {
  private var stringList: LazyList[String] = getAllStringsUpToLength(maxLength)

  /**
   * Returns the next available string, if possible.
   * @return an Option containing the next string generated if possible, else Option.empty
   */
  def getNextString(): Option[String] = this.synchronized {
    val next = stringList.headOption
    stringList = stringList.drop(1)
    next
  }

  private def getAllStringsUpToLength(maxLength: Int): LazyList[String] = maxLength match {
    case 0 => LazyList("")
    case 1 => LazyList.from(alphabet.toArray)
    case _ => LazyList.from(alphabet.toArray) ++ getAllStringsUpToLength(maxLength - 1).flatMap(s => alphabet.map(symbol => s + symbol))
  }
}

/**
 * This companion object provides some shorthands for standard alphabets.
 */
object ConcurrentStringProvider {
  def lowercaseLetters: List[String] = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
  def uppercaseLetters: List[String] = lowercaseLetters.map(l => l.toUpperCase)
  def numbers: List[String] = (0 to 9).map(i => i.toString).toList
  def symbols: List[String] = List(" ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~")
}