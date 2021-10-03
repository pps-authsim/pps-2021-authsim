package it.unibo.authsim.client.app.mvvm.util

import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ListBuffer


class ObservableListBufferTest extends AnyWordSpec:

  "An ObservableListBuffer" when {

    "Empty" should {
      "Have size 0" in {
        val observableList = ObservableListBuffer()

        assert(observableList.value.size == 0)
      }
    }

    "Initialized from list" should {
      "Have elements initialized with" in {
        val observableList = ObservableListBuffer(123)

        assert(observableList.value(0) == 123)
      }
    }

    "Value is added" should {
      "Have added value" in {
        var observableList = ObservableListBuffer[Int]()
        observableList += 42

        assert(observableList.value(0) == 42)
      }
    }

    "Value is added with attached listener" should {
      "Have listener from constructor be called" in {
        val mirrorList = ListBuffer[Int]()
        val copyAction: (Int => Unit) = value => mirrorList += value

        var observableList = ObservableListBuffer[Int](copyAction, copyAction)
        observableList += 42

        assert(mirrorList(0) == 42)
      }

      "Have listener from setter be called" in {
        val mirrorList = ListBuffer[Int]()
        val copyAction: (Int => Unit) = value => mirrorList += value

        var observableList = ObservableListBuffer[Int]()
        observableList.addOnAddSubscriber(copyAction)
        observableList += 42

        assert(mirrorList(0) == 42)
      }
    }

    "Value is removed" should {
      "Have removed value" in {
        var observableList = ObservableListBuffer(42)
        observableList -= 42

        assert(observableList.value.size == 0)
      }
    }

    "Value is removed with attached listener" should {
      "Have constructor listener be called" in {
        val mirrorList = ListBuffer[Int](42)
        val copyAction: (Int => Unit) = value => mirrorList -= value

        var observableList = ObservableListBuffer[Int](copyAction, copyAction, 42)
        observableList -= 42

        assert(mirrorList.size == 0)
      }

      "Have listener be called" in {
        val mirrorList = ListBuffer[Int](42)
        val copyAction: (Int => Unit) = value => mirrorList -= value

        var observableList = ObservableListBuffer[Int](42)
        observableList.addOnRemoveSubscriber(copyAction)
        observableList -= 42

        assert(mirrorList.size == 0)
      }
    }

    "List is cleared" should {
      "Have no elements" in {
        val observableList = ObservableListBuffer[Int](42)

        observableList.clear()

        assert(observableList.value.size == 0)
      }
    }

    "List equaled" should {
      "Be equal to another list with same content" in {
        val observableListBuffer = ObservableListBuffer(1, 2, 3)

        assert(observableListBuffer.hasSameValues(Seq(1, 2, 3)))
      }

      "Be not equal to another list with different content" in {
        val observableListBuffer = ObservableListBuffer(1, 2, 3)

        assert(!observableListBuffer.hasSameValues(Seq(4, 5)))
      }
    }

    "To string called" should {
      "Be as expected" in {
        val observableListBuffer = ObservableListBuffer("foo", "bar", "fizz")

        assert(observableListBuffer.toString().equals("ListBuffer(foo, bar, fizz)"))
      }
    }

  }

