package it.unibo.authsim.client.app.mvvm.util

import scala.collection.mutable.ListBuffer

class ObservableListBuffer[A]:

  private val wrappedList: ListBuffer[A] = ListBuffer()

  var onAdd: Option[(A => Unit)] = Option.empty
  var onRemove: Option[(A => Unit)] = Option.empty

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
  
  override def toString(): String = wrappedList.toString()

  override def equals(o: Any): Boolean = wrappedList.equals(o)

  override def hashCode(): Int = wrappedList.hashCode()


