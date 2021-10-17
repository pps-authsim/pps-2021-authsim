package it.unibo.authsim.client.app.mvvm.util

import scala.collection.{IterableOnce, Seq}
import scala.collection.mutable.ListBuffer

object ObservableListBuffer:

  /**
   * Constructs an empty ObservableListBuffer without any listeners
   * @param A data type
   * @return ObservableListBuffer
   */
  def apply[A](): ObservableListBuffer[A] = new ObservableListBuffer[A]()

  /**
   * Constructs an ObservableListBuffer from the given objects without any listeners
   * @param A data type
   * @return ObservableListBuffer
   */
  def apply[A](elems: A*): ObservableListBuffer[A] = new ObservableListBuffer(ListBuffer.from(elems))

  /**
   * Constructs an empty ObservableListBuffer with given listeners
   * @param onAdd on add event listener
   * @param onRemove on remove event listener
   * @tparam A data type
   * @return ObservableListBuffer
   */
  def apply[A](onAdd: (A => Unit), onRemove: (A => Unit)) =
    val observableListBuffer = new ObservableListBuffer[A]()
    observableListBuffer.onAddSubscribers += onAdd
    observableListBuffer.onRemoveSubscribers += onRemove
    observableListBuffer


  /**
   * Constructs a ObservableListBuffer from given values with given listeners
   * @param onAdd on add event listener
   * @param onRemove on remove event listener
   * @tparam A data type
   * @return ObservableListBuffer
   */
  def apply[A](onAdd: (A => Unit), onRemove: (A => Unit), elems: A*) =
    val observableListBuffer = new ObservableListBuffer[A](ListBuffer.from(elems))
    observableListBuffer.onAddSubscribers += onAdd
    observableListBuffer.onRemoveSubscribers += onRemove
    observableListBuffer


/**
 * An observable buffer that can be subscribed to on add or remove events
 * @param wrappedList (optional) initial collection, defaults to an empty list
 * @param onAdd (optional) define callback for add event
 * @param onRemove (optional) define callback for delete event
 * @tparam A type of lists's elements
 */
class ObservableListBuffer[A](
                               private val wrappedList: ListBuffer[A] = ListBuffer[A](),
                               private var onAddSubscribers: ListBuffer[(A => Unit)] = ListBuffer[(A => Unit)](),
                               private var onRemoveSubscribers: ListBuffer[(A => Unit)] = ListBuffer[(A => Unit)]()
                             ):

  /**
   * Mutably adds elements to the ObservableListBuffer
   * @param element element to be add
   * @return self
   */
  def +(element: A): ObservableListBuffer[A] =
    wrappedList += element
    onAddSubscribers.foreach(_.apply(element))
    return this


  /**
   * Mutably removes elements from the ObservableListBuffer
   * @param element element to be removed
   * @return self
   */
  def -(element: A): ObservableListBuffer[A] =
    wrappedList -= element
    onRemoveSubscribers.foreach(_.apply(element))
    return this

  /**
   * Clears list buffer of all the elements
   */
  def clear(): Unit =
    wrappedList.clear()

  /**
   * Adds a subscriber to the add event
   * @param onAddSubscriber on add event subscriber
   */
  def addOnAddSubscriber(onAddSubscriber: (A => Unit)): Unit =
    this.onAddSubscribers += onAddSubscriber

  /**
   * Adds a subscriber to the remove event
   * @param onRemoveSubscriber on remove event subscriber
   */
  def addOnRemoveSubscriber(onRemoveSubscriber: (A => Unit)): Unit =
    this.onRemoveSubscribers += onRemoveSubscriber

  /**
   * Deep copy of the current wrapper list state
   * @return - wrapped list copy
   */
  def value: ListBuffer[A] = ListBuffer.from(wrappedList)

  /**
   * true if both buffer iterable and ObservableListBuffer contain same values
   * @param elements iterable to be equaled with
   * @return if ObservableListBuffer has same elements as the iterable
   */
  def hasSameValues(elements: IterableOnce[A]) = this.wrappedList.sameElements(elements)

  override def toString(): String = wrappedList.toString()

  override def hashCode(): Int = wrappedList.hashCode()


