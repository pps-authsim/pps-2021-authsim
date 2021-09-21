package it.unibo.authsim.client.app.mvvm.util

import scala.collection.{IterableOnce, Seq}
import scala.collection.mutable.ListBuffer

object ObservableListBuffer:

  def apply[A](): ObservableListBuffer[A] = new ObservableListBuffer[A]()

  def apply[A](elems: A*): ObservableListBuffer[A] = new ObservableListBuffer(ListBuffer.from(elems))

  def apply[A](onAdd: (A => Unit), onRemove: (A => Unit)) =
    val observableListBuffer = new ObservableListBuffer[A]()
    observableListBuffer.onAdd = Option(onAdd)
    observableListBuffer.onRemove = Option(onRemove)
    observableListBuffer


  def apply[A](onAdd: (A => Unit), onRemove: (A => Unit), elems: A*) =
    val observableListBuffer = new ObservableListBuffer[A](ListBuffer.from(elems))
    observableListBuffer.onAdd = Option(onAdd)
    observableListBuffer.onRemove = Option(onRemove)
    observableListBuffer


/**
 * An observable buffer that can be singularly subscribed to on add or remove events
 * @param wrappedList (optional) initial collection, defaults to an empty list
 * @param onAdd (optional) define callback for add event
 * @param onRemove (optional) define callback for delete event
 * @tparam A type of lists's elements
 */
class ObservableListBuffer[A](
                               private val wrappedList: ListBuffer[A] = ListBuffer[A](),
                               private var onAdd: Option[(A => Unit)] = Option.empty,
                               private var onRemove: Option[(A => Unit)] = Option.empty
                             ):

  def +(element: A): ObservableListBuffer[A] =
    wrappedList += element
    onAdd.map(_.apply(element))
    return this


  def -(element: A): ObservableListBuffer[A] =
    wrappedList -= element
    onRemove.map(_.apply(element))
    return this

  def clear(): Unit =
    wrappedList.clear()

  def onAdd(onAdd: (A => Unit)): Unit =
    this.onAdd = Option(onAdd)

  def onRemove(onRemove: (A => Unit)): Unit =
    this.onRemove = Option(onRemove)

  /**
   * Deep copy of the current wrapper list state
   * @return - wrapped list copy
   */
  def value: ListBuffer[A] = ListBuffer.from(wrappedList)

  /**
   * @param observableListBuffer equaled list
   * @return true if both buffer lists have same values
   */
  def hasSameValues(elements: IterableOnce[A]) = this.wrappedList.sameElements(elements)

  override def toString(): String = wrappedList.toString()

  override def hashCode(): Int = wrappedList.hashCode()


