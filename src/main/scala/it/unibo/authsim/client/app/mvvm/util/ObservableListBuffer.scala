package it.unibo.authsim.client.app.mvvm.util

import scala.collection.mutable.ListBuffer

object ObservableListBuffer:

  def apply[A](): ObservableListBuffer[A] = new ObservableListBuffer[A]()

  def apply[A](elems: A*): ObservableListBuffer[A] = new ObservableListBuffer(ListBuffer.from(elems))

  def apply[A](onAdd: (A => Unit), onRemove: (A => Unit)) = {
    val observableListBuffer = new ObservableListBuffer[A]()
    observableListBuffer.onAdd = Option(onAdd)
    observableListBuffer.onRemove = Option(onRemove)
    observableListBuffer
  }

  def apply[A](onAdd: (A => Unit), onRemove: (A => Unit), elems: A*) = {
    val observableListBuffer = new ObservableListBuffer[A](ListBuffer.from(elems))
    observableListBuffer.onAdd = Option(onAdd)
    observableListBuffer.onRemove = Option(onRemove)
    observableListBuffer
  }

class ObservableListBuffer[A](
                               private val wrappedList: ListBuffer[A] = ListBuffer[A](),
                               var onAdd: Option[(A => Unit)] = Option.empty,
                               var onRemove: Option[(A => Unit)] = Option.empty
                             ):

  def +(element: A): ObservableListBuffer[A] = {
    wrappedList += element
    onAdd.map(_.apply(element))
    return this
  }

  def -(element: A): ObservableListBuffer[A] = {
    wrappedList -= element
    onRemove.map(_.apply(element))
    return this
  }

  def clear(): Unit =
    wrappedList.clear()

  /**
   * Deep copy of the current wrapper list state
   * @return - wrapped list copy
   */
  def value: ListBuffer[A] = ListBuffer.from(wrappedList)

  override def toString(): String = wrappedList.toString()

  override def equals(o: Any): Boolean = wrappedList.equals(o)

  override def hashCode(): Int = wrappedList.hashCode()


