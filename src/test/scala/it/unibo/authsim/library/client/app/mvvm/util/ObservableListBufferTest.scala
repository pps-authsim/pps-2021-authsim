package it.unibo.authsim.library.client.app.mvvm.util

import it.unibo.authsim.client.app.mvvm.util.ObservableListBuffer
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ListBuffer


class ObservableListBufferTest extends AnyWordSpec {

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
      "Have listener be called" in {
        var mirrorList = ListBuffer[Int]()
        val copyAction: (Int => Unit) = value => mirrorList += value

        var observableList = ObservableListBuffer[Int](copyAction, copyAction)
        observableList += 42

        assert(mirrorList(0) == 42)
      }
    }

    "Value is removed" should {
      "Have not removed value" in {
        var observableList = ObservableListBuffer(42)
        observableList -= 42

        assert(observableList.value.size == 0)
      }
    }


    "Value is removed with attached listener" should {
      "Have listener be called" in {
        var mirrorList = ListBuffer[Int](42)
        val copyAction: (Int => Unit) = value => mirrorList -= value

        var observableList = ObservableListBuffer[Int](copyAction, copyAction, 42)
        observableList -= 42

        assert(mirrorList.size == 0)
      }
    }

  }

}
