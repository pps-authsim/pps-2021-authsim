package it.unibo.authsim.library.dsl.alphabet

/**
 * An alphabet has a Set of symbols and an acceptor to check whether the symbols are correct.
 * @param alphabet The set of symbols of the alphabet.
 * @param acceptor The function used to check each symbol in the set.
 * @tparam T The specific type of the Alphabet.
 * @throw IllegalArgumentException when the symbols are not accepted by the defined acceptor.
 */
@throws[IllegalArgumentException]("if the alphabet is not acceptable")
trait Alphabet[T <: Alphabet[T]](val symbolSet: Set[String], protected val acceptor: String => Boolean) extends Set[String]:
  require(symbolSet forall acceptor)

  /**
   * Creates a new alphabet with the union of the symbol sets and a combination of the acceptors.
   *
   * Overriding methods can choose how to handle acceptors, be it a logical OR (to allow both symbols)
   * or any other logical combinator of them.
   *
   * @param other The other alphabet to include
   * @throws IllegalArgumentException when the other symbolSet is not compatible with this one.
   * @return A new Alphabet of the type of the called object.
   */
  @throws[IllegalArgumentException]("when the other symbolSet is not compatible with this")
  def and(other: Alphabet[_]): T

  /**
   * Forward method to wrapped Set.
   */
  final override def iterator = this.symbolSet.iterator

  /**
   * Forward method to wrapped Set.
   */
  final override def contains(elem: String) = this.symbolSet.contains(elem)

  /**
   * Forward method to wrapped Set.
   */
  final override def excl(elem: String) = this.symbolSet.excl(elem)

  /**
   * Forward method to wrapped Set.
   */
  final override def incl(elem: String) = this.symbolSet.incl(elem)

/**
 * An alphabet where symbols are words.
 */
@throws[IllegalArgumentException]("if the alphabet is not acceptable")
case class Dictionary(override val symbolSet: Set[String], override protected val acceptor: String => Boolean = s => !s.isBlank) extends Alphabet[Dictionary](symbolSet, acceptor):
  /**
   * Unifies two Alphabets in one Dictionary (as a Dictionary can contain every non-blank word) and uses the acceptor of the first addend.
   * @param other The other alphabet.
   * @return A new Dictionary with the word set equals to the union of the symbol set of the addends.
   */
  override def and(other: Alphabet[_]) = Dictionary(this.symbolSet concat other.symbolSet, this.acceptor)

/**
 * An alphabet where symbols are single characters.
 * @param symbolSet The set of symbols of the alphabet.
 * @param acceptor The function used to check each symbol in the set.
 */
@throws[IllegalArgumentException]("if the alphabet is not acceptable")
case class SymbolicAlphabet(override val symbolSet: Set[String], protected override val acceptor: String => Boolean = s => !s.isBlank && s.length == 1) extends Alphabet[SymbolicAlphabet](symbolSet, acceptor):
  /**
   * Create a new SymbolicAlphabet with the union of the symbol sets and the acceptor of the first addend.
   *
   * @param other The other alphabet to include
   * @return A new Alphabet of the type of the called object.
   */
  override def and(other: Alphabet[_]) = other match {
    case SymbolicAlphabet(symbolSet, acceptor) => SymbolicAlphabet(this.symbolSet concat symbolSet, this.acceptor)
    case _ => throw new IllegalArgumentException("The Alphabet argument has incompatible symbols with this addend acceptor. This two alphabets may not be addable, but if you think they are, try to invert the order of the addends.")
  }